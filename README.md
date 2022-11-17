# k-wordle-solver

Helper to solve Wordle.

Supported languages:

- en: https://www.nytimes.com/games/wordle/index.html
- by: https://ololophilolo.github.io/wordle-by/
- ru: https://wordle.belousov.one/

## Install

Download and extract binaries from [Releases](https://github.com/IamR3m/k-wordle-solver/releases) page.

Another way: git-clone project, assemble with gradle task `assembleDist` and find binaries in `build/distributions` directory

## Usage

On `windows` execute `bin/k-wordle-solver.bat`  
On `mac` or `linux` execute `bin/k-wordle-solver`

### Example
```sh
k-wordle-solver solve -l en
```

For more info see help
```sh
k-wordle-solver -h
```

### Note
Windows Command Line (cmd.exe) do not support coloring, so you can see weird literals like `←[34m`. Better use PowerShell
