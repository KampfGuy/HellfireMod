# Hellfire

A Minecraft mod with fictional strike flares and a placeable anti-air launcher. Every blast is a normal Minecraft explosion (the same system TNT uses), plus vanilla particles and sounds. Nothing here describes a real weapon, a real yield, or a real procedure. The green zone after a nuclear blast is just called radiation in the game. It is not a model of anything real.

The plane, bomb, missile, marker, and launcher models are original box models written for this mod. They were not downloaded and they are not copied from Mojang. A thrown flare is drawn as that flare's own item sprite (red, yellow, orange, or blue), not a shared 3D model. The strike plane's fuselage is about 6 blocks long.

## Items

Creative tab: **Hellfire**. It contains the four flares and the Mobile Anti-Air Platform. There is no radio and no plane you can fly. The flares are not consumed. The platform is used up when you place it, unless you are in creative mode.

The four flares charge like a bow: hold use, then release to throw. A short tap does nothing. On impact the flare calls its strike. It does not place a block.

| Item | How to use |
| --- | --- |
| Missile Strike (red) | Throw it. A red beam marks the landing and a missile comes down for one large Minecraft explosion. |
| Nuclear Strike (yellow) | Throw it. A bomber flies over and drops one nuclear bomb. The blast clears a crater (default radius 27, capped, bedrock and other unbreakable blocks stay) including wood, leaves, paths, plants, and built blocks. Then a green zone lingers for about a minute and hurts living things. The tooltip calls that radiation. |
| Napalm Strike (orange) | Throw it. Same pattern as Bombing Run: a plane drops a short line of bombs. Those impacts also set the ground on fire and keep it burning for about 30 seconds. |
| Bombing Run (blue) | Throw it. A plane drops a short line of normal bombs, then leaves. You cannot mount the plane. |
| Mobile Anti-Air Platform | Right-click a block to place a boxy green launcher. It watches about 32 blocks around itself. When a phantom, the mod's plane, a missile, a bomb, or another airborne mob is in range, it draws a green box on that target, locks on, and fires a shot that destroys it. Players are ignored unless they are gliding, and it never shoots the player who placed it. |

## Config

Values are in-game block counts and Minecraft explosion power. They are capped (crater radius 8–30, explosion powers up to 6) so a survival world stays playable.

- NeoForge and Forge: `config/hellfire-common.toml` after you launch the game once.
- Fabric: `config/hellfire.properties` (written on first launch).

If you already launched an older Hellfire, delete those config files or the old crater radius stays.

## Install

Built jars (1.3.0):

- `hellfire-neoforge-1.21.1-1.3.0.jar` — Minecraft **1.21.1**, NeoForge **21.1.250** or newer on the 21.1 line.
- `hellfire-forge-1.20.1-1.3.0.jar` — Minecraft **1.20.1**, Forge **47.4.0** or newer on the 47.x line.
- `hellfire-fabric-26.3-1.3.0.jar` — Minecraft **26.3**, Fabric Loader **0.19.5**, and Fabric API **0.161.0+26.3** (or a newer 26.3 build).

## Build

NeoForge 1.21.1 and Fabric 26.3 share the root Gradle 9.6 build:

```bash
./gradlew :neoforge-1.21.1:build :fabric-26.3:build
```

Forge 1.20.1 uses ForgeGradle 6, which needs Gradle 8.8, so it is its own build:

```bash
cd forge-1.20.1 && ./gradlew build
```

You need a JDK that can run Gradle (21 is fine for the root build; the Forge build targets Java 17 and Fabric targets Java 25 via toolchains).

## Layout

- `neoforge-1.21.1` and `fabric-26.3` are Gradle subprojects of the root build.
- `forge-1.20.1` is a separate Gradle project in this same repo, because one wrapper cannot satisfy both ForgeGradle 6 and Fabric Loom for 26.3.
