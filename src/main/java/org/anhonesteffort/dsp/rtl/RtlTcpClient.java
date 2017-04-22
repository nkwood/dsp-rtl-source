package org.anhonesteffort.dsp.rtl;

import org.anhonesteffort.dsp.util.ComplexNumber;

import java.io.Closeable;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.ByteBuffer;

public class RtlTcpClient implements Closeable {

  private final OutputStream writeStream;
  private final DataInputStream readStream;
  private final byte[] tunerInfo;

  public RtlTcpClient(String hostname, int hostPort) throws IOException {
    Socket socket = new Socket(hostname, hostPort);
    socket.setTcpNoDelay(true);

    writeStream = socket.getOutputStream();
    readStream  = new DataInputStream(socket.getInputStream());

    try {

      tunerInfo = readTunerInfo();

    } catch (IOException err) {
      this.close();
      throw err;
    }
  }

  private boolean charsMatch(byte[] bytes, char ... chars) {
    for (int i = 0; i < chars.length; i++) {
      if (bytes[i] != chars[i]) {
        return false;
      }
    }

    return true;
  }

  private byte[] readTunerInfo() throws IOException {
    byte[] bytes = new byte[12];
    readStream.readFully(bytes);

    if (!charsMatch(bytes, 'R', 'T', 'L', '0')) {
      throw new IOException("expecting 4 bytes of protocol identifier");
    } else {
      return bytes;
    }
  }

  public void readSamples(RtlSamples rtlSamples) throws IOException {
    byte[]          bytes   = rtlSamples.getBytes();
    ComplexNumber[] samples = rtlSamples.getSamples();

    readStream.readFully(bytes);

    for (int i = 0; i < (bytes.length - 1); i += 2) {
      samples[i / 2].setInPhase((bytes[i] & 0xff) / 255.0f);
      samples[i / 2].setQuadrature((bytes[i + 1] & 0xff) / 255.0f);
    }
  }

  public void sendCommand(Command command, int value) throws IOException {
    ByteBuffer buffer = ByteBuffer.wrap(new byte[5]);

    buffer.put(command.code);
    buffer.putInt(value);

    writeStream.write(buffer.array());
    writeStream.flush();
  }

  @Override
  public void close() throws IOException {
    writeStream.close();
  }

  @SuppressWarnings("unused")
  protected enum Command {
    SetFrequency(1),
    SetSampleRate(2),
    SetGainMode(3),
    SetGain(4),
    SetFrequencyCorrection(5),
    SetIfStage(6),
    SetTestMode(7),
    SetAgcMode(8),
    SetDirectSampling(9),
    SetOffsetTuning(10),
    SetRtlCrystal(11),
    SetTunerCrystal(12),
    SetTunerGainByIndex(13),
    SetTunerBandwidth(14),
    SetBiasTee(15);

    private final byte code;
    Command(int code) {
      this.code = (byte) code;
    }
  }

}
