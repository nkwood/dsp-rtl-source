/*
 * Copyright (C) 2017 An Honest Effort LLC.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package org.anhonesteffort.dsp.rtl;

import org.anhonesteffort.dsp.sample.SdrDriver;
import org.anhonesteffort.dsp.sample.SdrDriverProvider;
import org.anhonesteffort.dsp.util.ComplexNumber;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Optional;

public class RtlSdrDriverProvider implements SdrDriverProvider {

  private static final Logger log = LoggerFactory.getLogger(RtlSdrDriverProvider.class);
  private final RtlConfig config;

  public RtlSdrDriverProvider() {
    try {

      config = new RtlConfig();

    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public RtlSamplesEvent newInstance() {
    byte[]          bytes   = new byte[config.getSampleBufferSize()];
    ComplexNumber[] samples = new ComplexNumber[bytes.length / 2];

    for (int i = 0; i < samples.length; i++) {
      samples[i] = new ComplexNumber(0f, 0f);
    }

    return new RtlSamplesEvent(-1l, -1d, new RtlSamples(bytes, samples));
  }

  @Override
  public Optional<SdrDriver> getDriver() {
    try {

      return Optional.of(new RtlSdrDriver(
          config, new RtlTcpClient(config.getRtlTcpHostname(), config.getRtlTcpPort())
      ));

    } catch (Throwable err) {
      log.error("error building rtl sdr driver", err);
    }

    return Optional.empty();
  }

}
