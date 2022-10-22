package net.zeeraa.novacore.spigot.abstraction.enums;

import org.bukkit.entity.Entity;
import org.bukkit.event.entity.PlayerDeathEvent;

/**
 * A utility to get the death cause from a {@link PlayerDeathEvent}
 * 
 * @author Bruno
 * @since 2.0.0
 */
public enum DeathType {
	PROJECTILE_ARROW("was shot by", true), PROJECTILE_OTHER("was pummeled by", true), CACTUS("was pricked to death", false), CACTUS_COMBAT("walked into a cactus whilst trying to escape", true), DROWN("drowned", false), DROWN_COMBAT("drowned whilst trying to escape", true),
	ELYTRA_WALL("experienced kinetic energy", false), ELYTRA_WALL_COMBAT("experienced kinetic energy whilst trying to escape", true),
	EXPLOSION("blew up", false), EXPLOSION_COMBAT("was blown up by", true), INTENTIONAL_GAME_DESIGN("was killed by [Intentional Game Design]", false),
	FALL_SMALL("hit the ground too hard", false), FALL_SMALL_COMBAT("hit the ground too hard whilst trying to escape", true), FALL_BIG("fell from a high place", false),
	FALL_CLIMBING_LADDER("fell off a ladder", false), FALL_CLIMBING_VINE("fell off some vines", false), FALL_CLIMBING_WEEPING_VINE("fell off some weeping vines", false),
	FALL_CLIMBING_TWISTING_VINES("fell off some twisting vines", false), FALL_CLIMBING_SCAFFOLDING("fell off scaffolding", false),
	FALL_CLIMBING_OTHER("fell while climbing",false), FALL_WATER(new String[]{"death.fell.accident.water", "fell out of the water"}, false),
	FALL_STALAGMITE("was impaled on a stalagmite", false), FALL_STALAGMITE_COMBAT("was impaled on a stalagmite whilst fighting", true),
	ANVIL_FALL("was squashed by a falling anvil", false), ANVIL_FALL_COMBAT("was squashed by a falling anvil whilst fighting", true), BLOCK_FALL("was squashed by a falling block", false),
	BLOCK_FALL_COMBAT("was squashed by a falling block whilst fighting", true), STALAGTITE_FALL("was skewered by a falling stalactite", false), STALAGTITE_FALL_COMBAT("was skewered by a falling stalactite whilst fighting", true),
	FIRE_SOURCE("went up in flames", false), FIRE_SOURCE_COMBAT("walked into fire whilst fighting", true), FIRE_NATURAL("burned to death", false), FIRE_NATURAL_COMBAT("was burnt to a crisp whilst fighting", true),
	ROCKET_EXPLOSION("went off with a bang", false), ROCKET_EXPLOSION_COMBAT_RENAMED("went off with a bang due to a firework fired from", true), LAVA("tried to swim in lava", false),
	LAVA_COMBAT("tried to swim in lava to escape", true), LIGHTNING("was struck by lightning", false), LIGHTNING_COMBAT("was struck by lightning whilst fighting", true),
	MAGMA_BLOCK("discovered the floor was lava", false), MAGMA_BLOCK_COMBAT("walked into danger zone due to", true), MAGIC("was killed by magic", false),
	MAGIC_COMBAT_ACCIDENT("was killed by magic whilst trying to escape", true), MAGIC_COMBAT("was killed by", true), FROZEN("froze to death", false),
	FROZEN_COMBAT("was frozen to death by", true), COMBAT_NORMAL("was slain by", true), COMBAT_FIREBALL("was fireballed by", true), COMBAT_BEE("was stung to death", true, false),
	COMBAT_WITHER_SKULL(new String[]{"was shot by a skull from", "death.attack.witherSkull.item"}, true), SONIC_BOOM("was obliterated by a sonically-charged shriek", true, false),
	STARVING("starved to death", false), STARVING_COMBAT("starved to death whilst fighting", true), SUFFOCATION("suffocated in a wall", false),
	SUFFOCATION_COMBAT("suffocated in a wall whilst fighting", true), SUFFOCATION_CRAMMING("was squished too much", false), SUFFOCATION_CRAMMING_COMBAT("was squashed by", true),
	BUSH("was poked to death by a sweet berry bush", false), BUSH_COMBAT("was poked to death by a sweet berry bush whilst trying to escape", true),
	THORNS("was killed trying to hurt", true), TRIDENT("was impaled by", true), VOID("fell out of the world", false), VOID_COMBAT("didn't want to live in the same world as", true),
	EFFECT_WITHER("withered away", false), EFFECT_WITHER_COMBAT("withered away whilst fighting", true), GENERIC("died", true), GENERIC_COMBAT("died because of", true), PROJECTILE_FALL("was doomed to fall", false),
	PROJECTILE_FALL_COMBAT("was doomed to fall by", true), DRAGON_BREATH("was roasted in dragon breath", false), DRAGON_BREATH_COMBAT("was roasted in dragon breath by", true);

	private final String identifier;
	private final boolean combat;
	private final boolean addsKiller;
	private final boolean hasMultipleIdentifiers;
	private final String[] identifiers;
	DeathType(String identifier, boolean combat) {
		this.identifier = identifier;
		this.combat = combat;
		this.identifiers = new String[]{};
		this.hasMultipleIdentifiers = false;
		this.addsKiller = combat;
	}
	DeathType(String[] identifiers, boolean combat) {
		this.identifier = "";
		this.combat = combat;
		this.identifiers = identifiers;
		this.hasMultipleIdentifiers = true;
		this.addsKiller = combat;
	}
	DeathType(String identifier, boolean combat, boolean addsKiller) {
		this.identifier = identifier;
		this.combat = combat;
		this.identifiers = new String[]{};
		this.hasMultipleIdentifiers = false;
		this.addsKiller = addsKiller;
	}

	public String getIdentifier() {
		return identifier;
	}

	public boolean isCombat() {
		return combat;
	}

	public boolean addsKiller() {
		return addsKiller;
	}

	public boolean hasMultipleIdentifiers() {
		return hasMultipleIdentifiers;
	}

	public String[] getIdentifiers() {
		return identifiers;
	}


	public static DeathType getDeathFromEvent(PlayerDeathEvent e) {
		return getDeathFromEvent(e, null);
	}

	public static DeathType getDeathFromEvent(PlayerDeathEvent e, Entity customLastDamager) {

		String death = e.getDeathMessage().replace(e.getEntity().getName(), "");
		if (customLastDamager != null) {
			death = death.replace(customLastDamager.getName(), "");
		} else if (e.getEntity().getKiller() != null) {
				death = death.replace(e.getEntity().getKiller().getName(), "");
			}



		while (death.contains("  ")) {
			death = death.replace("  ", " ");
		}
		if (death.startsWith(" ")) {
			death = death.substring(1);
		}
		if (death.endsWith(" ")) {
			death = death.substring(0, death.length() - 1);
		}
		String withoutUsing = death.split(" using ")[0];

		String thornsExeption = "was killed trying " + death.split(" trying ")[1];
		for (DeathType type : DeathType.values()) {
			if (type.hasMultipleIdentifiers()) {
				for (String identifier : type.getIdentifiers()) {
					if (death.equalsIgnoreCase(identifier)) {
						return type;
					}  else if (withoutUsing.equalsIgnoreCase(identifier)) {
						return type;
					} else if (thornsExeption.equalsIgnoreCase(identifier)) {
						return type;
					}
				}
			} else {
				if (death.equalsIgnoreCase(type.getIdentifier())) {
					return type;
				}  else if (withoutUsing.equalsIgnoreCase(type.getIdentifier())) {
					return type;
				} else if (thornsExeption.equalsIgnoreCase(type.getIdentifier())) {
					return type;
				}
			}

		}
		return GENERIC;
	}
}