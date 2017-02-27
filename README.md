deep-pokemon
============

I don't like losing, so I turned to our machine overlords for help. Let's play Pokémon!

*The Pokémon logos, characters, and game mechanics are intellectual property of Nintendo. This project investigates computational techniques to manipulate an abstract, simulated version of the in-game Pokémon battle system. This software is purely a mathematical model and cannot be used as an alternative to the experience of playing the game in any way. This software is licensed under the [GPL](GPL.md).*

*submitted as a final project for the Computational Economics course at Vanderbilt University, spring 2017*

# Motivation

Pokémon is a complex game by both human and machine standards with many complex and intersecting game mechanics. Effective human play requires some familiarity with all of these. Additionally, many such mechanics are stochastic, which can foil even the best-laid plans (colloquially known as "hax"). Previous efforts to play Pokémon have relied on complicated heuristics, which have achieved some success. However, the complexity and stochasticity of Pokémon makes it difficult to construct useful or even admissible heuristics. This motivates a (mostly) heuristic-free approach which does not impose any complex, artificial, or manually-entered assumptions about gameplay.

Wikipedia has a detailed introduction to [the game mechanics](https://en.wikipedia.org/wiki/Gameplay_of_Pok%C3%A9mon#Pok.C3.A9mon_Battles). For further analysis of the approach, see the [initial proposal I submitted](proposal-pkmn.pdf).

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
