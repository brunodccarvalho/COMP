package compiler.codeGenerator;

import compiler.dag.DAGMember;
import compiler.symbols.MemberDescriptor;

public class StoreMember extends JVMInst {

    /**
     * 1: class descriptor
     */
    public static String CLASSTYPE = "L?;";
    /**
     * 1: class name path
     * 2: call variable name
     * 3: type of variable
     */
    public static String PUTFIELD = "\n\taload_0\n\tswap\n\tputfield ?/? ?";

    private MemberDescriptor memberDescriptor;

    StoreMember (DAGMember member)
    {
        this.memberDescriptor = member.getVariable();
    }

    @Override
    public String toString()
    {
        String memberName=memberDescriptor.getName();
        String className=memberDescriptor.getParentClass().getClassName();
        String type=memberDescriptor.getType().getName();
        String memberType=CodeGeneratorConstants.types.get(type);
        if(memberType==null)
            memberType=subst(StoreMember.CLASSTYPE, type);
        return subst(StoreMember.PUTFIELD, className,memberName,memberType);
    }


}
