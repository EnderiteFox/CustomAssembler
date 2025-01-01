package core.redstone_assembly.emulator;

import java.util.Map;

public record RedstoneAssemblyResult(Map<Byte, Byte> registryBank, Map<Byte, Byte> ram) {
    @Override
    public String toString() {
        StringBuilder str = new StringBuilder("Registry bank:\n");
        for (Byte reg : registryBank.keySet()) {
            str.append("r").append(reg).append(": ");
            for (int i = 0; i < 8; ++i) str.append(((registryBank.get(reg) << i) & 0b10000000) != 0 ? '1' : '0');
            str.append("   ").append(registryBank.get(reg)).append("\n");
        }
        str.append("\nRAM:\n");
        for (Byte address : ram.keySet().stream().sorted().toList()) {
            str.append(address).append(": ");
            for (int i = 0; i < 8; ++i) str.append(((ram.get(address) << i) & 0b10000000) != 0 ? '1' : '0');
            str.append("   ").append(ram.get(address)).append("\n");
        }
        return str.toString();
    }

    public String toStringFull() {
        for (byte i = 0; i < 16; ++i) if (!registryBank.containsKey(i)) registryBank.put(i, (byte) 0);
        for (int i = 0; i < 1024; ++i) if (!ram.containsKey((byte) i)) ram.put((byte) i, (byte) 0);
        return this.toString();
    }
}
