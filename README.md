# Alchimia Vitae (1.21.4 Update)

![Minecraft](https://img.shields.io/badge/Minecraft-1.21.4-brightgreen.svg)
![Java](https://img.shields.io/badge/Java-21-orange.svg)
![Slimefun](https://img.shields.io/badge/Slimefun4-RC--37+-blue.svg)

Slimefun addon that adds alchemy, herbalism, magic plants, divine crafting, and custom infusions to Minecraft.  
Originally created by **[Apeiros-46B](https://github.com/Apeiros-46B)** for the 2021 Slimefun Addon Jam, modernized and updated for **Minecraft 1.21.4 (Paper/Purpur)** and **Java 21**.

---

## 🌟 Original Author & Credits
- **Original Creator:** [Apeiros-46B](https://github.com/Apeiros-46B)
- **Original Repository:** [Apeiros-46B/AlchimiaVitae](https://github.com/Apeiros-46B/AlchimiaVitae)
- All original game mechanics, lore, and textures are credited to the original author. This fork maintains compatibility with modern Minecraft / Paper / Slimefun 4.

---

## 📋 Requirements
- **Server:** Paper / Purpur 1.21.4 (or forks)
- **Java:** Java 21+
- **Dependencies:**
  - [Slimefun4](https://github.com/Slimefun/Slimefun4) (RC-37+ / 1.21.4 compatible build)

---

## 🔮 Features

### ⚔️ Tools & Resources
- **Soul Collector:** Increases experience dropped by mobs and grants a chance to obtain *Condensed Souls* when slaying mobs.
- **Magic Plants (Light & Dark):** Created by infusing saplings in the *Plant Infusion Chamber*. Used to craft essential essences and potions.
- **EXP Crystallizer & EXP Crystals:** Store and distill raw knowledge and experience into pure crystalline energy.
- **Alchemical Metals:** Ingot metals such as *Illumium*, *Darksteel*, and *Mystery Metal*.

### 🏺 Crafters & Altars
- **Divine Altar:** Advanced transmutation altar used to craft complex materials like *Molten Mystery Metal*.
- **Cosmic Cauldron:** Advanced cauldron for brewing specialized concoctions:
  - **Benevolent Brew:** Bestows powerful protective absorption shields.
  - **Malevolent Concoction:** High-potency offensive splash potion.
  - **Potion of Osmosis:** Absorbs active potion effects and distills them into a reusable *Coruscating Potion*.
- **Altar of Infusion:** Infuses weapons and tools with exclusive custom enchantments and abilities.

---

## 🛠️ Building & Running Locally

### Compile with Gradle
```bash
./gradlew build
```
The compiled jar will be in `build/libs/AlchimiaVitae-1.21.4-all.jar`.

### Run Test Server (Paper 1.21.4)
```bash
./gradlew runServer
```
