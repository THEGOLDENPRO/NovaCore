package net.zeeraa.novacore.spigot.abstraction.enums;

public enum Attribute{



    GENERIC_MAX_HEALTH("generic.max_health", "generic.maxHealth"),
    GENERIC_FOLLOW_RANGE("generic.follow_range", "generic.followRange"),
    GENERIC_KNOCKBACK_RESISTANCE("generic.knockback_resistance", "generic.knockbackResistance"),
    GENERIC_MOVEMENT_SPEED("generic.movement_speed", "generic.movementSpeed"),
    GENERIC_FLYING_SPEED("generic.flying_speed", "generic.flyingSpeed"),
    GENERIC_ATTACK_DAMAGE("generic.attack_damage", "generic.attackDamage"),
    GENERIC_ATTACK_KNOCKBACK("generic.attack_knockback", "generic.attackKnockback"),
    GENERIC_ATTACK_SPEED("generic.attack_speed", "generic.attackSpeed"),
    GENERIC_ARMOR("generic.armor", "generic.armor"),
    GENERIC_ARMOR_TOUGHNESS("generic.armor_toughness", "generic.armorToughness"),
    GENERIC_LUCK("generic.luck", "generic.luck"),
    HORSE_JUMP_STRENGTH("horse.jump_strength", "horse.jumpStrength"),
    ZOMBIE_SPAWN_REINFORCEMENTS("zombie.spawn_reinforcements", "zombie.spawnReinforcements");


    private String key;
    private String pre1_16;
    Attribute(String key, String pre1_16) {
        this.key = key;
        this.pre1_16 = pre1_16;
    }


    public String getPre1_16Key() {
        return pre1_16;
    }

    public String getKey() {
        return key;
    }


}
