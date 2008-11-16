import mjs.processing.mobile.mbluetooth.*;

MBluetooth bt;

void setup() {
  bt = MBluetooth.getInstance(this);
  bt.discover();
  noLoop();
}

void libraryEvent(Object library, int event, Object data) {
  if (library == bt) {
    switch (event) {
      case MBluetooth.EVENT_DISCOVER_DEVICE:
        MDevice device = (MDevice) data;
        println(device.name() + " discovered.");
        break;
      case MBluetooth.EVENT_DISCOVER_DEVICE_COMPLETED:
        MDevice[] devices = (MDevice[]) data;
        println(devices.length + " devices discovered.");
        break;
    }
  }
}
