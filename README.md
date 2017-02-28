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

- [Pokémon Showdown](http://pokemonshowdown.com/), an open-source battle simulator.

# TODO

1. [ ] Produce a proof of concept client which interfaces with the Pokémon Showdown simulator.
    - Documentation produced to explain how to use the client to battle against other players on a server.
2. [ ] Generate toy game and script to play the game which invokes TensorFlow to generate each new action to be performed.
    - Documentation produced to explain the rules of the toy game, how to run the script, and how to evaluate its accuracy.
3. [ ] Encode game mechanics into a script which uses TensorFlow to play entire games against human opponents on the simulator.
    - Documentation produced to explain how the bot is trained, what information it stores, and how its effectiveness will be evaluated.

# Notes
## Reverse Engineering the Pokémon Showdown Client
- most commands are sent/handled in client in client/js/client-battle.js, in server in server/battle-engine.js
- all updates to game state are exactly the same as in a [replay](OU-2015-03-13-getbacker-crashinboombang.html)
    - everything sent over single websocket
        - this is then echoed to the console log with `<<` in front
            - e.g. this [example console output](play.pokemonshowdown.com-1488188260083.log)
    - client has a huge number of functions which process game state
        - message creation has no centralized documentation or even function to create them (lol)
            - *most* socket msgs sent from `Battle.prototype.send(...args)` (in `Battle` class)
                - this is defined in `room-battle.js` in the server repo
            - search for calls to:
                - `this.send()`
                - `\|[^\|]+\|`, for calls which FORMAT THEMSELVES
            - file `battle-engine.js` in server MIGHT have all of the calls relevant to battle
                - still spread across many functions

## Implementing MCTS and Derivatives
- plug-and-play implementations are not available for MCTS in *any* widely-used machine learning framework
    - descriptions and basic implementations *do* exist
        - e.g. [RocAlphaGo](https://github.com/Rochester-NRT/RocAlphaGo) (specifically [mcts.py](https://github.com/Rochester-NRT/RocAlphaGo/blob/develop/AlphaGo/mcts.py)) and [this blog post](https://jeffbradberry.com/posts/2015/09/intro-to-monte-carlo-tree-search/)
        - there are **at most 10** possible actions in 1v1 Pokémon (4 moves and up to 5 switches), which may help in traversing tree
            - state includes current Pokémon, so don't need to have individual move names, just move index (as long as team stays static)
                - can make this into move names if we want to go to team generation?!
- docs for [UCT](papers/uct.pdf) and [AlphaGo's approach](papers/AlphaGoNaturePaper.pdf) in repo
    - also see [RocAlphaGo's explanation](https://github.com/Rochester-NRT/RocAlphaGo/wiki/01.-Home)
- can use tf or other lib for the neural net part
- [this description of AlphaGo](https://github.com/Rochester-NRT/RocAlphaGo/wiki/01.-Home) seems to make a 3-phase approach reasonable
    1. pure tree search (value)
    2. add supervised policy network (guessing expert play)
    3. fast policy (maybe)
- check out [cfr](http://cs.gettysburg.edu/~tneller/modelai/2013/cfr/index.html) (and [here](https://www.quora.com/What-is-an-intuitive-explanation-of-counterfactual-regret-minimization)?)

## Evaluation Methods
- consider openai gym?
    - think this is intended to evaluate a generic algorithm on many games, not a single game
    - can still peruse their docs to see if they have any interesting ideas
- consider an evaluation metric of perhaps success rate vs other players on ladder?
    - (to be used only on validation set?)
    - this is essentially what a ladder ranking is trying to do
        - it's kinda like chess's `ELO`
