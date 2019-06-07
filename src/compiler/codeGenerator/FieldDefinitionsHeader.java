package compiler.codeGenerator;

import compiler.symbols.MemberDescriptor;

public class FieldDefinitionsHeader extends JVMInst {

    /**
     * 1. Field Name
     * 2. Field Type Descriptor
     */
    public static String FIELDDEF = "\n.field protected '?' ?";
    private final MemberDescriptor[] memberDescriptors;

    public FieldDefinitionsHeader(MemberDescriptor[] memberDescriptors) {
        this.memberDescriptors = memberDescriptors;
    }

    private String generateFieldDefinition(MemberDescriptor memberDescriptor) {
        String fieldName = memberDescriptor.getName();
        String fieldDescriptor = memberDescriptor.getType().getBytecodeString();
        return subst(FIELDDEF, fieldName, fieldDescriptor);
    }

    @Override
    public String toString() {
        String fieldDefinitionsHeader = new String();
        for(MemberDescriptor memberDescriptor: memberDescriptors) {
            String fieldDef = this.generateFieldDefinition(memberDescriptor);
            fieldDefinitionsHeader = fieldDefinitionsHeader.concat(fieldDef);
        }
        return fieldDefinitionsHeader;
    }
}