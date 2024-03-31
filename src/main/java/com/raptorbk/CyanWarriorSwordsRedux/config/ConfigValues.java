package com.raptorbk.CyanWarriorSwordsRedux.config;

public enum ConfigValues {
    /*FOOD(6, new ConfigProperty("FoodItems", new ListConfigValue()
            .put(new ConfigProperty("FriedEgg"))
            .put(new ConfigProperty("BerryPie"))
            .put(new ConfigProperty("HoneyedApple"))
    ))*/

    BEAST_ESSENCE(6, new ConfigProperty("BeastEssence")),
    DARK_ESSENCE(6, new ConfigProperty("DarkEssence")),
    EARTH_ESSENCE(6, new ConfigProperty("EarthEssence")),
    ENDER_ESSENCE(6, new ConfigProperty("EnderEssence")),
    FIRE_ESSENCE(6, new ConfigProperty("FireEssence")),
    LIGHT_ESSENCE(6, new ConfigProperty("LightEssence")),
    MIXED_ESSENCE(6, new ConfigProperty("MixedEssence")),
    THUNDER_ESSENCE(6, new ConfigProperty("ThunderEssence")),
    WATER_ESSENCE(6, new ConfigProperty("WaterEssence")),
    WIND_ESSENCE(6, new ConfigProperty("WindEssence"));

    private final int version;
    private final ConfigProperty configProperty;

    ConfigValues(int version, ConfigProperty configProperty) {
        this.version = version;
        this.configProperty = configProperty;
    }

    public ConfigProperty getProperty() {
        return configProperty;
    }

    public int getVersion() {
        return version;
    }

    public ConfigValueType getType() {
        return configProperty.value().getType();
    }

    public static ConfigValues getByName(String name) {
        for (ConfigValues value : values()) {
            if (value.getProperty().key().equals(name)) {
                return value;
            }
        }
        return null;
    }
}