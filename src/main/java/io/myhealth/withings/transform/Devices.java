package io.myhealth.withings.transform;

import com.withings.api.user.Device;
import com.withings.api.user.DeviceList;

public class Devices {

    public static String find(DeviceList devices, int modelId) {
        return devices.getDevices()
                .stream()
                .filter(d -> d.getModelId() == modelId)
                .map(Device::getModel)
                .findAny()
                .orElse("Unknown");
    }
}
