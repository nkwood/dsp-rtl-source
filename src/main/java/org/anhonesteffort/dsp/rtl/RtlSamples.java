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

import org.anhonesteffort.dsp.sample.Samples;
import org.anhonesteffort.dsp.util.ComplexNumber;

public class RtlSamples extends Samples {

  private final byte[] bytes;

  public RtlSamples(byte[] bytes, ComplexNumber[] samples) {
    super(samples);
    this.bytes = bytes;
  }

  public byte[] getBytes() {
    return bytes;
  }

}
