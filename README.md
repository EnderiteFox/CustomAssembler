# CustomAssembler
An assembler for my custom assembly language, which will be used to build a Minecraft processor  

# Language specification

| Decimal OPCODE | Instruction Code | Full Instruction Name | Instruction Type | Full Description                                                    |
|----------------|------------------|-----------------------|------------------|---------------------------------------------------------------------|
| 0              | NOP              | No Operation          | Zero             | Does nothing                                                        |
| 1              | HALT             | Halt                  | Zero             | Ends the program                                                    |
| 2              | ADD              | Addition              | Register         | RegA = RegB + RegC                                                  |
| 3              | ADDI             | Immediate Addition    | Immediate        | RegA += DATA                                                        |
| 4              | AND              | Bitwise AND           | Register         | RegA = RegB & RegC                                                  |
| 5              | RSH              | Right Shift           | Reduced Register | RegA = RegB >> 1                                                    |
| 6              | NOR              | Bitwise NOR           | Register         | RegA = !(RegB \| RegC)                                              |
| 7              | XOR              | Bitwise XOR           | Register         | RegA = RegB ^ RegC                                                  |
| 8              | SUB              | Substraction          | Register         | RegA = RegB - RegC                                                  |
| 9              | JMP              | Jump                  | Addressed        | Sets the Program Counter to ADDRESS                                 |
| 10             | BRH              | Branch                | Addressed        | Jumps to DATA if the ALU flags are corresponding to the given flags |
| 11             | CAL              | Call                  | Addressed        | Pushes PC + 1 to the call stack, then jumps to ADDRESS              |
| 12             | RET              | Return                | Zero             | Pops the call stack and jumps to that address                       |
| 13             | LOD              | Load                  | Immediate        | Loads the content of the RAM at DATA to RegA                        |
| 14             | WRI              | Write                 | Immediate        | Writes the content of RegA to DATA in the RAM                       |
| 15             |                  |                       |                  | Empty slot                                                          |

# Instruction types

Instruction types are mainly handled by the assembler and do nothing once the program has been assembled  
There are 4 instruction types:

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

### Addressed
Takes flags and an address

| 4 bits | 2 bits | 12 bits |
|--------|--------|---------|
| OPCODE | Flags  | ADDRESS |

# Hardware specifications

The register bank has 15 8-bit registers, and a zero register  
The program memory is a ROM with 1024 16-bit instructions  
The ALU has an overflow and a zero-result flag  
The RAM can store 1024 16-bit numbers  

# Assember features
Instruction numbers for the immediate and address instruction types support decimal, binary and hexadecimal numbers  
Labels are also supported  
