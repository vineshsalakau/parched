# Parched Skeletons — Fabric Mod for 1.21.10

Backports the **Parched** mob from Minecraft 1.21.11 to 1.21.10.

## What it does
- Adds Parched skeletons that spawn in **desert biomes** (replacing ~50% of skeletons)
- **Immune to sunlight** — they don't burn during the day
- Fire **Arrows of Weakness** (30 second duration)
- **Slower fire rate** — 3.5 seconds between shots (vs. 2s for regular skeletons)
- **16 HP (8 hearts)** — slightly squishier than regular skeletons

---

## How to build

### Prerequisites
- **Java 21 JDK** — https://adoptium.net/
- **IntelliJ IDEA** (recommended) or any IDE

### Steps

1. **Check versions** — open `gradle.properties` and verify the versions are still current at https://fabricmc.net/develop/
   Replace `yarn_mappings` and `fabric_version` with the latest for 1.21.10 if needed.

2. **Open in IntelliJ** — File → Open → select the `parched-mod` folder.
   Let Gradle sync finish.

3. **Add the texture** (see below)

4. **Build** — open a terminal in the project root and run:
   ```
   ./gradlew build          # Linux/Mac
   gradlew.bat build        # Windows
   ```

5. **Find the JAR** — it will be at:
   ```
   build/libs/parched-mod-1.0.0.jar
   ```
   Drop this JAR into your server's `mods/` folder (and each client's `mods/` folder too).

---

## Getting the texture

The mod needs a texture file at:
```
src/main/resources/assets/parched/textures/entity/parched.png
```

**Option A (recommended) — extract from 1.21.11:**
1. Download the 1.21.11 client JAR from your Minecraft launcher's `versions` folder
2. Open the JAR with 7-Zip or similar
3. Navigate to `assets/minecraft/textures/entity/skeleton/parched.png`
4. Copy it to the path above

**Option B — use skeleton texture as placeholder:**
Copy `skeleton.png` from any Minecraft version as a placeholder so the game doesn't show a missing texture. The parched will look like a regular skeleton until you get the real texture.

---

## Troubleshooting

**"GENERIC_MAX_HEALTH not found" compile error:**
In some Yarn builds the attribute is named `MAX_HEALTH` without the `GENERIC_` prefix.
Open `ParchedEntity.java` and change:
```java
EntityAttributes.GENERIC_MAX_HEALTH  →  EntityAttributes.MAX_HEALTH
EntityAttributes.GENERIC_MOVEMENT_SPEED  →  EntityAttributes.MOVEMENT_SPEED
EntityAttributes.GENERIC_FOLLOW_RANGE  →  EntityAttributes.FOLLOW_RANGE
EntityAttributes.GENERIC_ARMOR  →  EntityAttributes.ARMOR
```

**"ProjectileUtil.createArrowProjectile not found":**
Try the alternative constructor directly:
```java
ArrowEntity projectile = new ArrowEntity(world, this, arrowStack, null);
```

**Gradle version error:**
Update `gradle/wrapper/gradle-wrapper.properties` to use the latest Gradle 8.x from https://gradle.org/releases/
