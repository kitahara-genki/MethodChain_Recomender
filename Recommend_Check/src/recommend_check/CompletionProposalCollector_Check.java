package recommend_check;

import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.ui.text.java.CompletionProposalCollector;
import org.eclipse.jdt.ui.text.java.JavaContentAssistInvocationContext;

public class CompletionProposalCollector_Check extends CompletionProposalCollector{
//tigau
	public CompletionProposalCollector_Check(ICompilationUnit cu) {
		super(cu);
	}

	public CompletionProposalCollector_Check(IJavaProject project) {
		super(project);
	}

	public void setInvocationContext(JavaContentAssistInvocationContext context) {
		super.setInvocationContext(context);
		System.out.println("context = "+context);
	}
	public CompletionProposalCollector_Check(ICompilationUnit cu, boolean ignoreAll) {
		super(cu, ignoreAll);
	}
}
