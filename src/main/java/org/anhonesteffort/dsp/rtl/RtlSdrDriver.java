package org.anhonesteffort.dsp.rtl;

import org.anhonesteffort.dsp.sample.Samples;
import org.anhonesteffort.dsp.sample.SdrDriver;
import org.anhonesteffort.dsp.util.ChannelSpec;
import org.anhonesteffort.dsp.rtl.RtlTcpClient.Command;

import java.io.IOException;

public class RtlSdrDriver extends SdrDriver {

  protected static final long[] SUPPORTED_RATES = new long[] {
        240_000l,   288_000l,   960_000l,
      1_200_000l, 1_440_000l, 2_016_000l,
      2_208_000l, 2_400_000l, 2_880_000l
  };

  private final RtlConfig    config;
  private final RtlTcpClient rtlClient;

  protected RtlSdrDriver(RtlConfig config, RtlTcpClient rtlClient) {
    super(config.getMaxSampleRate(), config.getMinFrequency(), config.getMaxFrequency());

    this.config    = config;
    this.rtlClient = rtlClient;

    applyConfig();
  }

  private void applyConfig() {
    try {

      rtlClient.sendCommand(Command.SetDirectSampling, 0);
      rtlClient.sendCommand(Command.SetGain, config.getMasterGain());
      rtlClient.sendCommand(Command.SetFrequencyCorrection, config.getFrequencyCorrection());

    } catch (IOException err) {
      throw new RuntimeException("error applying configuration", err);
    }
  }

  private long toSupportedRate(long minSampleRate) {
    for (long supportedRate : SUPPORTED_RATES) {
      if (supportedRate >= minSampleRate) {
        return supportedRate;
      }
    }

    return SUPPORTED_RATES[SUPPORTED_RATES.length - 1];
  }

  @Override
  protected long setSampleRate(long minSampleRate) {
    int supportedRate = Double.valueOf(toSupportedRate(minSampleRate)).intValue();

    try {

      rtlClient.sendCommand(Command.SetSampleRate, supportedRate);
      return supportedRate;

    } catch (IOException err) {
      throw new RuntimeException("error setting sample rate", err);
    }
  }

  @Override
  protected double setFrequency(double frequency) {
    try {

      rtlClient.sendCommand(Command.SetFrequency, Double.valueOf(frequency).intValue());
      return frequency;

    } catch (IOException err) {
      throw new RuntimeException("error setting frequency", err);
    }
  }

  @Override
  protected void fillBuffer(Samples samples) {
    try {

      rtlClient.readSamples((RtlSamples) samples);

    } catch (IOException err) {
      throw new RuntimeException("error reading samples", err);
    }
  }

  @Override
  protected ChannelSpec onStart() {
    long   sampleRate = setSampleRate(config.getMaxSampleRate());
    double frequency  = setFrequency(config.getMaxFrequency());

    return new ChannelSpec(frequency, sampleRate, sampleRate);
  }

  @Override
  protected void onStop() {
    super.onStop();
    try {

      rtlClient.close();

    } catch (IOException err) {
      throw new RuntimeException(err);
    }
  }

}
