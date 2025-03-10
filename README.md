# CustomAssembler
An assembler and an emulator for my custom assembly language, which will be used to build a Minecraft processor  

# Language specification

| Decimal OPCODE | Instruction Code | Full Instruction Name | Instruction Type  | Full Description                                                                  |
|----------------|------------------|-----------------------|-------------------|-----------------------------------------------------------------------------------|
| 0              | NOP              | No Operation          | Zero              | Does nothing                                                                      |
| 1              | HALT             | Halt                  | Zero              | Ends the program                                                                  |
| 2              | ADD              | Addition              | Register          | RegA = RegB + RegC                                                                |
| 3              | ADDI             | Immediate Addition    | Immediate         | RegA += DATA                                                                      |
| 4              | AND              | Bitwise AND           | Register          | RegA = RegB & RegC                                                                |
| 5              | RSH              | Right Shift           | Reduced Register  | RegA = RegB >> 1                                                                  |
| 6              | NOR              | Bitwise NOR           | Register          | RegA = !(RegB \| RegC)                                                            |
| 7              | XOR              | Bitwise XOR           | Register          | RegA = RegB ^ RegC                                                                |
| 8              | SUB              | Substraction          | Register          | RegA = RegB - RegC                                                                |
| 9              | JMP              | Jump                  | Program Addressed | Sets the Program Counter to ADDRESS                                               |
| 10             | BRH              | Branch                | Program Addressed | Jumps to DATA if the ALU flags are corresponding to the given flags               |
| 11             | CAL              | Call                  | Program Addressed | Pushes PC + 1 to the call stack, then jumps to ADDRESS                            |
| 12             | RET              | Return                | Zero              | Pops the call stack and jumps to that address                                     |
| 13             | LOD              | Load                  | Memory Addressed  | Loads the content of the RAM at the address stored in RegB, plus OFFSET, to RegA  |
| 14             | WRI              | Write                 | Memory Addressed  | Writes the content of RegA to the address stored in RegB, plus OFFSET, in the RAM |
| 15             |                  |                       |                   | Empty slot                                                                        |

# Instruction types

There are multiple formats for the instructions, as different instructions need different arguments.  
The assembler takes instruction types into account, and arguments that would always be zero can simply be omitted, and the assembler will set them to zero.  
Additionnaly, for the Program Addressed instruction type, the condition argument can be omitted, and will be set to zero.  
There are 5 instruction types:  

### Register
Takes 3 registers

| 4 bits | 4 bits | 4 bits | 4 bits |
|--------|--------|--------|--------|
| OPCODE | RegA   | RegB   | RegC   |

### Reduced Register
Takes 2 registers

| 4 bits | 4 bits | 4 bits | 4 bits |
|--------|--------|--------|--------|
| OPCODE | RegA   | RegB   | ZERO   |

### Zero
Takes no argument

| 4 bits | 12 bits |
|--------|---------|
| OPCODE | ZERO    |

### Immediate
Takes a register and an immediate number

| 4 bits | 4 bits | 8 bits |
|--------|--------|--------|
| OPCODE | RegA   | DATA   |

### Program Addressed
Takes flags and an address

| 4 bits | 2 bits | 12 bits |
|--------|--------|---------|
| OPCODE | Flags  | ADDRESS |

### Memory Addressed
Takes two registers and an immediate offset

| 4 bits | 4 bits | 4 bits | 4 bits |
|--------|--------|--------|--------|
| OPCODE | RegA   | RegB   | OFFSET |


# Hardware specifications

The register bank has 15 8-bit registers, and a zero register.  
The program memory is a ROM with 1024 16-bit instructions.  
The ALU has an overflow and a zero-result flag (respectively on the first and second bit). Every instruction that make use of the ALU are setting the ALU flags accordingly.  
The RAM can store 256 8-bit numbers.  

# Assembler features
Instruction numbers for the immediate and address instruction types support decimal, binary and hexadecimal numbers  
Labels are also supported  
You can also define your own aliases in the program by using `ALIAS /<regex>/ TO /<regex>/`  
Aliases can be recursive, turning an alias into another alias  
Some aliases are predefined for redstone assembly  

# Predefined aliases

| From                       | To                | Short Description                      | Full Description                                  |
|----------------------------|-------------------|----------------------------------------|---------------------------------------------------|
| `^reset r([0-9]\|1[0-5])$` | `and r$1 r0 r0`   | reset \<reg> => and \<reg> r0 r0       | Resetting to 0                                    |
| `^lsh r([0-9]\|1[0-5])$`   | `add r$1 r$1 r$1` | lsh \<reg> => add \<reg> \<reg> \<reg> | Left shift using addition (reg + reg == reg << 1) |
| `^r([0-9]\|1[0-5])\+\+`    | `addi r$1 1`      | \<reg>++ => addi \<reg> 1              | Shortcut to increment                             |
| `^r([0-9]\|1[0-5])--`      | `addi r$1 -1`     | \<reg>-- => addi \<reg> -1             | Shortcut to decrement                             |

# Emulator

An emulator executes the machine code and displays the registry bank and the memory after execution  
