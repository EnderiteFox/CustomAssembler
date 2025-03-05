addi r15 11

// First 2 terms
addi r1 1
wri r1 r0 0
add r15 r15 r0
brh 0b01 end
wri r1 r0 1
r1--
r15--
brh 0b01 end
r15--
brh 0b01 end

// Calculation
calc:lod r2 r1 0
lod r3 r1 1
add r4 r2 r3
wri r4 r1 2
r1++
r15--
brh 0b01 end
jmp calc

// End
end:halt