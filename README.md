deep-pokemon
============

I don't like losing, so I turned to our machine overlords for help. Let's play Pokémon [^1] !

*submitted as a final project for the Computational Economics course at Vanderbilt University, spring 2017*

# Motivation

Pokémon is a complex game by both human and machine standards with many complex and intersecting game mechanics. Effective human play requires some familiarity with all of these. Additionally, many such mechanics are stochastic, which can foil even the best-laid plans (colloquially known as "hax"). Previous efforts to play Pokemon have relied on complicated heuristics [^3] [^4], which have achieved some success. However, the complexity and stochasticity of Pokemon makes it difficult to construct useful or even admissible heuristics. This motivates a (mostly) heuristic-free approach which does not impose any complex, artificial, or manually-entered assumptions about gameplay.

For more analysis of the approach, see the [initial proposal I submitted](proposal-pkmn.pdf).

# Tools Used

- Python [^8], a widely-used general-purpose programming language with many libraries for efficient numerical computation.
- Pokemon Showdown [^5], an open-source [^6] battle simulator.
- TensorFlow [^7], an open-source machine learning framework for Python which efficiently implements many models and training techniques.

# TODO

1. [ ] Produce a proof of concept client which interfaces with the [Pokemon Showdown simulator][^5].
    - Documentation produced to explain how to use the client to battle against other players on a server.
2. [ ] Generate toy game and script to play the game which invokes TensorFlow to generate each new action to be performed.
    - Documentation produced to explain the rules of the toy game, how to run the script, and how to evaluate its accuracy.
3. [ ] Encode game mechanics into a script which uses TensorFlow to play entire games against human opponents on the simulator.
    - Documentation produced to explain how the bot is trained, what information it stores, and how its effectiveness will be evaluated.

[^1]: The Pokémon logos, characters, and game mechanics are intellectual property of Nintendo. This project investigates computational techniques to play an abstract, simulated version of the Pokémon battle system. This software is not an alternative in any way to the experience of playing the game.

[^2]: https://en.wikipedia.org/wiki/Gameplay_of_Pok%C3%A9mon#Pok.C3.A9mon_Battles

[^3]: http://doublewise.net/pokemon/

[^4]: http://www.smogon.com/forums/threads/pokemon-showdown-ai-bot.3547689/

[^5]: http://pokemonshowdown.com/

[^6]: https://github.com/Zarel/Pokemon-Showdown

[^7]: http://tensorflow.org/

[^8]: https://www.python.org/
