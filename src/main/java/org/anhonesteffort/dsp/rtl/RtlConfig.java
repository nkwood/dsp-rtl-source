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

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class RtlConfig {

  private final String rtlTcpHostname;
  private final int    rtlTcpPort;
  private final long   maxSampleRate;
  private final long   minFrequency;
  private final long   maxFrequency;
  private final int    sampleBufferSize;
  private final int    frequencyCorrection;
  private final int    masterGain;

  public RtlConfig() throws IOException {
    Properties properties = new Properties();
    properties.load(new FileInputStream("rtl.properties"));

    rtlTcpHostname      = properties.getProperty("rtl_tcp_hostname");
    rtlTcpPort          = Integer.parseInt(properties.getProperty("rtl_tcp_port"));
    maxSampleRate       = Long.parseLong(properties.getProperty("max_sample_rate"));
    minFrequency        = Long.parseLong(properties.getProperty("min_frequency"));
    maxFrequency        = Long.parseLong(properties.getProperty("max_frequency"));
    sampleBufferSize    = Integer.parseInt(properties.getProperty("sample_buffer_size"));
    frequencyCorrection = Integer.parseInt(properties.getProperty("frequency_correction"));
    masterGain          = Integer.parseInt(properties.getProperty("master_gain"));
  }

  public String getRtlTcpHostname() {
    return rtlTcpHostname;
  }

  public int getRtlTcpPort() {
    return rtlTcpPort;
  }

  public long getMaxSampleRate() {
    return maxSampleRate;
  }

  public long getMinFrequency() {
    return minFrequency;
  }

  public long getMaxFrequency() {
    return maxFrequency;
  }

  public int getSampleBufferSize() {
    return sampleBufferSize;
  }

  public int getFrequencyCorrection() {
    return frequencyCorrection;
  }

  public int getMasterGain() {
    return masterGain;
  }

}
