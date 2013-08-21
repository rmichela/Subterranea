Overview
========
Subterranea is a modification of the Vanilla terrain generator that switches the above and below ground spaces so your world has 192 layers below sea level and 64 layers above. Great for Mole Man worlds.

![Example world](http://dev.bukkit.org/media/images/61/708/Subterranea.png)

Features
========
* Creates a world mostly underground instead of mostly full of air.
* Planned support for [Multiverse2](http://dev.bukkit.org/bukkit-plugins/multiverse-core/)
* Fully compatible with [GiantCaves](http://dev.bukkit.org/bukkit-plugins/giant-caves/)
* All the expected above ground features of the vanilla terrain generator: villages, temples, trees, etc.
* All the expected below ground features of the vanilla terrain generator: caves, ores, lakes, lava, etc.
* Increased ore generation - since the world is three times as deep, ores range three times as high

Installation
============
1. Put the plugin in your plugins directory
2. Add a worlds section to your bukkit.yml or use Multiverse
3. Install a world switching plugin to go to your new world

```
worlds:
  [worldname]:
    generator: Subterranea
```

_Note:_ If you use "world" for [worldname] in your bukkit.yml, Subterranea will run in your default world.
