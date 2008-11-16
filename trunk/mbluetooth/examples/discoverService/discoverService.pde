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
        println(length(devices) + " devices discovered.");
		if (length(devices) > 0) {
          devices[0].discover();
        }
        break;
      case MBluetooth.EVENT_DISCOVER_SERVICE:
        MService[] services = (MService[]) data;
        println(services.length + " services discovered.");
        break;
      case MBluetooth.EVENT_DISCOVER_SERVICE_COMPLETED:
        println("Service discovery complete.");
        break;
    }
  }
}
