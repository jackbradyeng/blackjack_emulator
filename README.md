# Blackjack Simulator

## Introduction

This project is a fully interactive blackjack simulator, designed to replicate the experience of playing blackjack at a
modern casino. The program has two modes, interactive and simulation. Interactive mode allows you to play blackjack
using the command line interface. Simulation mode allows you to simulate a playing strategy and collect valuable
statistics based on the results. Specifically, it uses the Monte Carlo method, a powerful statistical tool for
approximating probabilities and expected value.<br/>

## Features

The blackjack simulator includes a number of commonly found features, including:
**insurance** - a mechanic in blackjack that allows the player to place a side bet in the event that the dealer makes a
natural blackjack; **splitting** - an option that allows the player to take their original hand and split it into two
separate hands if both initial cards posses the same numerical value; and **doubling** - an option that allows the
player to double the size of their bet after receiving their first two cards.<br/>

But it also includes a number of advanced features not commonly found in other implementations. For instance, the simulator
allows for **multi-betting** - the ability to play multiple hands simultaneously - as well as **back-betting** - the act
of placing a bet on another's player's hand but surrendering any agency in that hand's action. Most Australian casinos
allow back-betting, however it is not commonly found in blackjack implementations on the internet.<br/>

## Simulation Findings

The results of the simulation verify existing data, which suggest that the house possesses a 0.5-2% edge in Blackjack.
This means that for every 100 dollar bet, the player can expect to lose 0.50-2.00 dollars over the long run. Note that
this applies even when the player makes the most mathematically optimal decision for each hand. Deviations from optimal
strategy result in an  even lower expected value, thereby inflating the house edge. This finding assumes four to six
decks in rotation, a 1:1 payout ratio for regular wins, a 3:2 payout ratio for player blackjacks, and a house strategy
of standing on hard 17 and drawing on "soft" 17 (a 17  which includes an Ace). For reliable findings, it is recommended
to use 500,000 - 1 million iterations for the Monte Carlo method as this reduces the variance found when using small
sample sizes.<br/>

## Installation Instructions

1. git clone [https://github.com/jackbradyeng/blackjack_emulator.git]
2. cd blackjack_emulator
3. mvn install