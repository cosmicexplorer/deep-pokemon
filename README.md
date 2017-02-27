deep-pokemon
============

I don't like losing, so I turned to our machine overlords for help. Let's play Pokémon!

*The Pokémon logos, characters, and game mechanics are intellectual property of Nintendo. This project investigates computational techniques to manipulate an abstract, simulated version of the in-game Pokémon battle system. This software is purely a mathematical model and cannot be used as an alternative to the experience of playing the game in any way. This software is licensed under the [GPL](GPL.md).*

*This project was submitted as a final project for the Spring 2017 Computational Economics course at Vanderbilt University.*

# Motivation

Pokémon is a complex game by both human and machine standards with many complex and intersecting game mechanics. Furthermore, Pokémon battles are subject to many stochastic effects, and participants have incomplete information. Previous efforts to play Pokémon have often relied on complicated heuristics designed by expert humans, which have achieved some success. However, the complexity and stochasticity of Pokémon makes it very difficult to construct generally applicable heuristics. This motivates a (mostly) heuristic-free approach which does not impose any complex, artificial, or manually-entered assumptions about gameplay.

# Background

Wikipedia has a detailed introduction to [the Pokémon game mechanics](https://en.wikipedia.org/wiki/Gameplay_of_Pok%C3%A9mon#Pok.C3.A9mon_Battles). For further analysis of the approach, see the [initial proposal I submitted](proposal-pkmn.pdf).

# Tools Used

- [Python](https://www.python.org), a widely-used general-purpose programming language with many libraries for efficient numerical computation.
- [Pokémon Showdown](http://pokemonshowdown.com/), an open-source battle simulator.
- [TensorFlow](TensorFlow), an open-source machine learning framework for Python which efficiently implements many models and training techniques.

# TODO

1. [ ] Produce a proof of concept client which interfaces with the Pokémon Showdown simulator.
    - Documentation produced to explain how to use the client to battle against other players on a server.
2. [ ] Generate toy game and script to play the game which invokes TensorFlow to generate each new action to be performed.
    - Documentation produced to explain the rules of the toy game, how to run the script, and how to evaluate its accuracy.
3. [ ] Encode game mechanics into a script which uses TensorFlow to play entire games against human opponents on the simulator.
    - Documentation produced to explain how the bot is trained, what information it stores, and how its effectiveness will be evaluated.
