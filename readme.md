# Color Simulation Game

This is a JavaFX application that simulates a grid of cells where each cell can randomly change its color or adopt the average color of its neighbors in each round. The simulation allows the user to interact with the grid, including adjusting parameters and pausing individual cells.
## Features

- **Customizable Grid Size**: The user can specify the number of rows and columns to create the grid.
- **Probability of Color Change**: The user can set a probability p (between 0 and 1) which determines how likely a cell is to change its color in each round.
- **Randomized Delays**: Users specify a base delay (in milliseconds). The delay between updates for each cell is randomized between 0.5 × delay and 1.5 × delay, and this value changes every round.
- **Random Color Assignment**: Initially, each cell is assigned a random color.
- **Color Averaging**: In each round, a cell may either change its color randomly based on probability p or calculate the average color of its neighboring cells and adopt that color.
- **Pause Cells**: By clicking on any cell, the user can pause it. A paused cell will not change color nor will it influence the color of its neighbors.

## Prerequisites

Before running the application, ensure that you have the following:

    Java 21 or higher
    JavaFX SDK (for graphical components)

## Getting Started

git clone <repository_url>
cd <project_directory>

Set up JavaFX: If you don’t have JavaFX set up yet, you can download the JavaFX SDK from here. Then, configure your IDE (like IntelliJ IDEA or Eclipse) to include the JavaFX libraries in your project.

## How it Works

Initial Setup: Upon starting the simulation, each cell in the grid is assigned a random color.
Simulation Rounds: In each round, cells can either:
- Change color randomly with probability p.
- Adopt the average color of its neighbors if the probability condition is not met.
Paused Cells: Cells that are paused do not change color and do not affect the average color of their neighbors. Clicking on a paused cell will unpause it and allow it to participate in future rounds.

## Example

    Set grid size to 5 rows and 5 columns.
    Set probability p to 0.3.
    Set a delay of 500 milliseconds.
    Start the simulation.
    Click on any cell to pause it and watch how the surrounding cells adjust accordingly.

## Notes

- The grid operates in a toroidal manner, meaning that the edge cells are neighbors to the opposite edge cells (e.g., the top-left corner cell is adjacent to the bottom-right corner cell).
- The color blending is simplified to averaging RGB values, which results in smooth transitions between colors.
- each cell works as an individual thread.

## Author:
Michał Szandarowski

