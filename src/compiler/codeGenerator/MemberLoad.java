package compiler.codeGenerator;

import compiler.symbols.MemberDescriptor;
import compiler.dag.DAGMember;

/**
 * MemberLoad
 */
public class MemberLoad extends BaseByteCode {
    /**
     * 1: class descriptor
     */
    public static String CLASSTYPE = "L?;";
    /**
     * 1: class name path
     * 2: call variable name
     * 3: type of variable
     */
    public static String GETFIELD = "\taload 0\n\tgetfield ?/? ?";

    private MemberDescriptor memberDescriptor;

    MemberLoad (DAGMember member)
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
            memberType=subst(MemberLoad.CLASSTYPE, type);
        return subst(MemberLoad.GETFIELD, className,memberName,memberType) + "\n";
    }
    
}