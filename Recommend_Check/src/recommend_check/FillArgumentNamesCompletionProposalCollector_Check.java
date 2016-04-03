package recommend_check;

import org.eclipse.jdt.core.CompletionProposal;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.Signature;
import org.eclipse.jdt.internal.corext.util.JavaModelUtil;
import org.eclipse.jdt.internal.ui.JavaPlugin;
import org.eclipse.jdt.internal.ui.text.java.FilledArgumentNamesMethodProposal;
import org.eclipse.jdt.internal.ui.text.java.LazyGenericTypeProposal;
import org.eclipse.jdt.internal.ui.text.java.LazyJavaCompletionProposal;
import org.eclipse.jdt.internal.ui.text.java.ParameterGuessingProposal;
import org.eclipse.jdt.ui.text.java.CompletionProposalCollector;
import org.eclipse.jdt.ui.text.java.IJavaCompletionProposal;
import org.eclipse.jdt.ui.text.java.JavaContentAssistInvocationContext;
import org.eclipse.jface.preference.IPreferenceStore;

@SuppressWarnings("restriction")
public class FillArgumentNamesCompletionProposalCollector_Check extends CompletionProposalCollector {
	private final boolean fIsGuessArguments;

	public FillArgumentNamesCompletionProposalCollector_Check(
			JavaContentAssistInvocationContext context) {
		super(context.getCompilationUnit(), true);
		setInvocationContext(context);
		char[][] cc = context.getCoreContext().getExpectedTypesSignatures();
	//	char[][] cc = context.getCoreContext().getExpectedTypesKeys();
		/*for(int i=0;i<cc.length;i++){
			for(int j=0;j<cc[i].length;j++)System.out.println(cc[i][j]);
			System.out.println();
		}*/
		//System.out.println("corecontext = "+ context.getCoreContext().getEnclosingElement()+ "\n++++++++++++++++++++++++++");
		if(context.getExpectedType()!=null){
			System.out.println("****************\nexpectedType = "
					+ context.getExpectedType().toString()+"\n*******************");
		}
		//check---------------------------------------------------
		/*	CompletionContext context2 = context.getCoreContext();
			if (context2 != null) {
				char[][] expectedTypes = context2.getExpectedTypesSignatures();
				if ((expectedTypes != null) && (expectedTypes.length > 0)) {
					IJavaProject project = getCompilationUnit().getJavaProject();
					if (project != null) {
						try {
							IType fType = project.findType(SignatureUtil.stripSignatureToFQN(String.valueOf(expectedTypes[0])));
							System.out.println("IType " + fType);
						} catch (JavaModelException x) {
							JavaPlugin.log(x);
						}
					}
				}
			}*/
		//--------------------------------------------------------
		
		IPreferenceStore preferenceStore = JavaPlugin.getDefault()
				.getPreferenceStore();
		
		this.fIsGuessArguments = preferenceStore
				.getBoolean("content_assist_guess_method_arguments");
		if (preferenceStore.getBoolean("content_assist_fill_method_arguments")) {
			setRequireExtendedContext(true);
		}
	}

	protected IJavaCompletionProposal createJavaCompletionProposal(
			CompletionProposal proposal) {
		switch (proposal.getKind()) {
		case 6:
		case 24:
		case 26:
			return createMethodReferenceProposal(proposal);
		case 9:
			return createTypeProposal(proposal);
		}
		// 候補の値の型、必要となる型ではない
		//System.out.println("createJavaCompletionProposal "+ super.createJavaCompletionProposal(proposal).getDisplayString());
		return super.createJavaCompletionProposal(proposal);
	}

	private IJavaCompletionProposal createMethodReferenceProposal(
			CompletionProposal methodProposal) {
		String completion = String.valueOf(methodProposal.getCompletion());
		if ((completion.length() == 0)
				|| ((completion.length() == 1) && (completion.charAt(0) == ')'))
				|| (Signature.getParameterCount(methodProposal.getSignature()) == 0)
				|| (getContext().isInJavadoc())) {
			return super.createJavaCompletionProposal(methodProposal);
		}
		LazyJavaCompletionProposal proposal = null;
		proposal = ParameterGuessingProposal.createProposal(methodProposal,
				getInvocationContext(), this.fIsGuessArguments);
		if (proposal == null) {
			proposal = new FilledArgumentNamesMethodProposal(methodProposal,
					getInvocationContext());
		}
		return proposal;
	}

	IJavaCompletionProposal createTypeProposal(CompletionProposal typeProposal) {
		ICompilationUnit cu = getCompilationUnit();
		if ((cu == null)
				|| ((getContext() != null) && (getContext().isInJavadoc()))) {
			return super.createJavaCompletionProposal(typeProposal);
		}
		IJavaProject project = cu.getJavaProject();
		if (!shouldProposeGenerics(project)) {
			return super.createJavaCompletionProposal(typeProposal);
		}
		char[] completion = typeProposal.getCompletion();
		//System.out.println("???????????????????????????\ntypeProposal"+ completion);
		if ((completion.length > 0)
				&& ((completion[(completion.length - 1)] == ';') || (completion[(completion.length - 1)] == '.'))) {
			return super.createJavaCompletionProposal(typeProposal);
		}
		LazyJavaCompletionProposal newProposal = new LazyGenericTypeProposal(
				typeProposal, getInvocationContext());
		return newProposal;
	}

	private final boolean shouldProposeGenerics(IJavaProject project) {
		String sourceVersion;

		if (project != null) {
			sourceVersion = project.getOption(
					"org.eclipse.jdt.core.compiler.source", true);
		} else {
			sourceVersion = JavaCore
					.getOption("org.eclipse.jdt.core.compiler.source");
		}
		return JavaModelUtil.is50OrHigher(sourceVersion);
	}
}
