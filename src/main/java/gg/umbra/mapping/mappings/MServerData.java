package gg.umbra.mapping.mappings;

import gg.umbra.mapping.MappedClasses;
import gg.umbra.mapping.Mapping;
import gg.umbra.mapping.MappingField;

public class MServerData
extends Mapping {
    private static final String SERVER_IP_FIELD_NAME = "serverIP";
    private final MappingField serverIpField;

    private String readServerIp(Object serverData) {
        return (String)this.serverIpField.getObject(serverData);
    }

    public static String getServerIp(MServerData mapping, Object serverData) {
        return mapping.readServerIp(serverData);
    }

    public MServerData() {
        super(MappedClasses.uR);
        this.serverIpField = this.J(SERVER_IP_FIELD_NAME, true, String.class);
    }
}

