Overview
========
Subterranea is a modification of the Vanilla terrain generator that switches the above and below ground 
spaces so your world has 192 layers below sea level and 64 layers above. Great for Mole Man worlds.

![Example world](http://dev.bukkit.org/media/images/61/708/Subterranea.png)

Features
========
* Creates a world mostly underground instead of mostly full of air.
* Fully supports [Multiverse2](http://dev.bukkit.org/bukkit-plugins/multiverse-core/)
* Compatible with [GiantCaves](http://dev.bukkit.org/bukkit-plugins/giant-caves/)
* All the expected above ground features of the vanilla terrain generator: villages, temples, trees, etc.
* All the expected below ground features of the vanilla terrain generator: caves, ores, lakes, lava, etc.
* Increased ore generation - since the world is three times as deep, ores range three times as high
* Underground biomes - find hidden mushroom caverns and mysterious underground trees burried in the depths
* Silverfish colonies - rare but terrifying

Installation
============
1. Put the plugin in your plugins directory
2. Add a worlds section to your bukkit.yml or use Multiverse
3. Visit your new world and start exploring

```
worlds:
  [worldname]:
    generator: Subterranea[:options]
```

__Notes:__ 
* If you use "world" for [worldname] in your bukkit.yml, Subterranea will run in your default world.
* [:options] can be omitted to use the default settings. See below for details.

Using Subterranea with Multiverse
=================================
To use Subterranea with Multiverse, use the following sequence of commands
1. `mv create world_name NORMAL -g "Subterranea[:options]"`
2. `mv tp world_name`
 
__Notes:__ 
* [:options] can be omitted to use the default settings. See below for details.
 
Subterranea Options
===================
The Subterranea world generator supports a number of options. Options are passed into the generator using the 
[:options] string in the above examples. Always put a colon between the word Subterranea and the options string.

_-underground-biomes:_ (true or false, default true) Enables or disables underground bionme creation

_-silverfish:_ (true or false, default true) Enables or disables silverfish colony creation hidden in the stone.

_-giant-caves:_ (default sxz=500,sy=175,cutoff=65,miny=40,maxy=160) Enables or disables the Giant Caves plugin.
An optional configuration string can be passed into this option to configure the Giant Caves generator. See the
Giant Caves documentation for a description of each setting and how it affects cave creation.

__Multiverse Example:__

```
mv create DeepWorld NORMAL -g "Subterranea:-underground-biomes true -silverfish true 
         -giant-caves sxz=500,sy=175,cutoff=65,miny=10,maxy=180"
```

_Create a new Subterranea world called DeepWorld with underground biomes, silverfish, and gaint caves from layer 10 to layer 180_
