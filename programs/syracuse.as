// Number of iterations
addi r14 20

// First number
addi r15 19

wri r15 r1 0
add r2 r0 r15

// Calculation
calcbegin:add r3 r0 r0
r3++
and r3 r2 r3
brh 0b01 even
jmp odd

// Even
even:rsh r2 r2
jmp loopend

// Odd
odd:add r3 r2 r0
add r2 r3 r3
add r2 r2 r3
addi r2 1

// End of calculation
loopend:r1++
wri r2 r1 0
r14--
brh 0b01 programend
jmp calcbegin

// End of program
programend:halt