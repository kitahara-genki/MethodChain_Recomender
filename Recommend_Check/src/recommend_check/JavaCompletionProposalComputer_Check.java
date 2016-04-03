package recommend_check;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.internal.corext.util.Messages;
import org.eclipse.jdt.internal.ui.text.java.AnonymousTypeCompletionProposal;
import org.eclipse.jdt.internal.ui.text.java.JavaCompletionProposalComputer;
import org.eclipse.jdt.internal.ui.text.java.JavaMethodCompletionProposal;
import org.eclipse.jdt.ui.PreferenceConstants;
import org.eclipse.jdt.ui.text.java.CompletionProposalCollector;
import org.eclipse.jdt.ui.text.java.ContentAssistInvocationContext;
import org.eclipse.jdt.ui.text.java.JavaContentAssistInvocationContext;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.text.ITextViewer;
import org.eclipse.jface.text.contentassist.ICompletionProposal;
import org.eclipse.jface.text.contentassist.IContextInformation;
import org.eclipse.jface.text.contentassist.IContextInformationExtension;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.keys.IBindingService;

@SuppressWarnings("restriction")
public class JavaCompletionProposalComputer_Check extends
JavaCompletionProposalComputer {

	protected CompletionProposalCollector createCollector(
			JavaContentAssistInvocationContext context) {
		CompletionProposalCollector collector = createCollector2(context);
		collector.setIgnored(13, false);
		collector.setIgnored(1, false);
		collector.setIgnored(27, false);
		collector.setIgnored(2, false);
		collector.setIgnored(25, false);
		collector.setIgnored(3, false);
		collector.setIgnored(4, false);
		collector.setIgnored(5, false);
		collector.setIgnored(7, false);
		collector.setIgnored(12, false);
		collector.setIgnored(6, false);
		collector.setIgnored(26, false);
		collector.setIgnored(24, false);
		collector.setIgnored(8, false);
		collector.setIgnored(11, false);
		collector.setIgnored(10, false);
		return collector;
	}

	protected CompletionProposalCollector createCollector2(
			JavaContentAssistInvocationContext context) {
		if (PreferenceConstants.getPreferenceStore().getBoolean(
				"content_assist_fill_method_arguments")) {
			
			return new FillArgumentNamesCompletionProposalCollector_Check(context);
		}
		return new CompletionProposalCollector_Check(context.getCompilationUnit(),
				true);
	}
	
	protected int guessContextInformationPosition(
			ContentAssistInvocationContext context) {
		return guessMethodContextInformationPosition(context);
	}



	private static final class ContextInformationWrapper implements
	IContextInformation, IContextInformationExtension {
		private final IContextInformation fContextInformation;
		private int fPosition;

		public ContextInformationWrapper(IContextInformation contextInformation) {
			this.fContextInformation = contextInformation;
		}

		public String getContextDisplayString() {
			return this.fContextInformation.getContextDisplayString();
		}

		public Image getImage() {
			return this.fContextInformation.getImage();
		}

		public String getInformationDisplayString() {
			return this.fContextInformation.getInformationDisplayString();
		}

		public int getContextInformationPosition() {
			return this.fPosition;
		}

		public void setContextInformationPosition(int position) {
			this.fPosition = position;
		}

		public boolean equals(Object object) {
			if ((object instanceof ContextInformationWrapper)) {
				return this.fContextInformation
						.equals(((ContextInformationWrapper) object).fContextInformation);
			}
			return this.fContextInformation.equals(object);
		}

		public int hashCode() {
			return this.fContextInformation.hashCode();
		}
	}

	private static final long JAVA_CODE_ASSIST_TIMEOUT = Long.getLong(
			"org.eclipse.jdt.ui.codeAssistTimeout", 5000L).longValue();
	private String fErrorMessage;
	private final IProgressMonitor fTimeoutProgressMonitor;

	public JavaCompletionProposalComputer_Check() {
		this.fTimeoutProgressMonitor = createTimeoutProgressMonitor(JAVA_CODE_ASSIST_TIMEOUT);
	}


	/*protected final int guessMethodContextInformationPosition(
	ContentAssistInvocationContext context) {
int contextPosition = context.getInvocationOffset();

IDocument document = context.getDocument();
JavaHeuristicScanner scanner = new JavaHeuristicScanner(document);
int bound = Math.max(-1, contextPosition - 2000);

int pos = contextPosition - 1;
for (;;) {
	int paren = scanner.findOpeningPeer(pos, bound, '(', ')');
	if (paren == -1) {
		break;
	}
	int token = scanner.previousToken(paren - 1, bound);
	if ((token == 2000) || (token == 14)) {
		return paren + 1;
	}
	pos = paren - 1;
}
return contextPosition;
}*/

	private List<IContextInformation> addContextInformations(
			JavaContentAssistInvocationContext context, int offset) {
		List<ICompletionProposal> proposals = internalComputeCompletionProposals(
				offset, context);
		List<IContextInformation> result = new ArrayList(proposals.size());
		List<IContextInformation> anonymousResult = new ArrayList(proposals.size());
		for (Iterator<ICompletionProposal> it = proposals.iterator(); it
				.hasNext();) {
			ICompletionProposal proposal = (ICompletionProposal) it.next();
			IContextInformation contextInformation = proposal
					.getContextInformation();
			if (contextInformation != null) {
				ContextInformationWrapper wrapper = new ContextInformationWrapper(
						contextInformation);
				wrapper.setContextInformationPosition(offset);
				if ((proposal instanceof AnonymousTypeCompletionProposal)) {
					anonymousResult.add(wrapper);
				} else {
					result.add(wrapper);
				}
			}
		}
		if (result.size() == 0) {
			return anonymousResult;
		}
		return result;
	}

	public List<IContextInformation> computeContextInformation(
			ContentAssistInvocationContext context, IProgressMonitor monitor) {
		if ((context instanceof JavaContentAssistInvocationContext)) {
			JavaContentAssistInvocationContext javaContext = (JavaContentAssistInvocationContext) context;

			int contextInformationPosition = guessContextInformationPosition(javaContext);
			List<IContextInformation> result = addContextInformations(
					javaContext, contextInformationPosition);
			return result;
		}
		return Collections.emptyList();
	}

	public List<ICompletionProposal> computeCompletionProposals(
			ContentAssistInvocationContext context, IProgressMonitor monitor) {
		if ((context instanceof JavaContentAssistInvocationContext)) {
			JavaContentAssistInvocationContext javaContext = (JavaContentAssistInvocationContext) context;
			return internalComputeCompletionProposals(
					context.getInvocationOffset(), javaContext);
		}
		return Collections.emptyList();
	}

	private List<ICompletionProposal> internalComputeCompletionProposals(
			int offset, JavaContentAssistInvocationContext context) {
		ICompilationUnit unit = context.getCompilationUnit();
		if (unit == null) {
			return Collections.emptyList();
		}
		ITextViewer viewer = context.getViewer();
		System.out.println("aaa・ ・ ");
		CompletionProposalCollector collector = createCollector(context);
		collector.setInvocationContext(context);

		collector.setAllowsRequiredProposals(2, 9, true);
		collector.setAllowsRequiredProposals(2, 23, true);
		collector.setAllowsRequiredProposals(2, 21, true);

		collector.setAllowsRequiredProposals(6, 9, true);
		collector.setAllowsRequiredProposals(6, 23, true);
		collector.setAllowsRequiredProposals(6, 22, true);

		collector.setAllowsRequiredProposals(26, 9, true);

		collector.setAllowsRequiredProposals(27, 9, true);
		collector.setAllowsRequiredProposals(1, 9, true);

		collector.setAllowsRequiredProposals(9, 9, true);

		collector.setFavoriteReferences(getFavoriteStaticMembers());
		try {
			Point selection = viewer.getSelectedRange();
			if (selection.y > 0) {
				collector.setReplacementLength(selection.y);
			}
			unit.codeComplete(offset, collector, this.fTimeoutProgressMonitor);
			//System.out.println("bbb");
		} catch (OperationCanceledException localOperationCanceledException) {
			IBindingService bindingSvc = (IBindingService) PlatformUI
					.getWorkbench().getAdapter(IBindingService.class);
			String keyBinding = bindingSvc
					.getBestActiveBindingFormattedFor("org.eclipse.ui.edit.text.contentAssist.proposals");
			this.fErrorMessage = Messages.format(JavaTextMessage_Check.CompletionProcessor_error_javaCompletion_took_too_long_message,
					keyBinding);
		} catch (JavaModelException x) {
			Shell shell = viewer.getTextWidget().getShell();
			//System.out.println("Errororororrorororr");
			if (x.isDoesNotExist()
					) {
				MessageDialog
				.openInformation(
						shell,
						JavaTextMessage_Check.CompletionProcessor_error_notOnBuildPath_title,
						JavaTextMessage_Check.CompletionProcessor_error_notOnBuildPath_message);
			} else {
				ErrorDialog
				.openError(
						shell,
						JavaTextMessage_Check.CompletionProcessor_error_accessing_title,
						JavaTextMessage_Check.CompletionProcessor_error_accessing_message,
						x.getStatus());
			}
		}
		ICompletionProposal[] javaProposals = collector
				.getJavaCompletionProposals();
		int contextInformationOffset = guessMethodContextInformationPosition(context);
		//System.out.println(contextInformationOffset);
		if (contextInformationOffset != offset) {
			for (int i = 0; i < javaProposals.length; i++) {
				//System.out.println("javaProposals"+ i +".getAdditionalProposalInfo()" + javaProposals[i].getAdditionalProposalInfo());
				if ((javaProposals[i] instanceof JavaMethodCompletionProposal)) {

					JavaMethodCompletionProposal jmcp = (JavaMethodCompletionProposal) javaProposals[i];
					jmcp.setContextInformationPosition(contextInformationOffset);
					//System.out.println("jmcp "+ i + jmcp.getStyledDisplayString());
				}
			}
		}
		List<ICompletionProposal> proposals = new ArrayList(
				Arrays.asList(javaProposals));//実際に返ってくるもの
		//System.out.println("---------------------------------------");
		//for(int i = 0; i < proposals.size(); i++)System.out.println(proposals.get(i).getDisplayString());
		if (proposals.size() == 0) {
			String error = collector.getErrorMessage();
			if (error.length() > 0) {
				this.fErrorMessage = error;
			}
		}
		return proposals;
	}

	private IProgressMonitor createTimeoutProgressMonitor(final long timeout) {
		return new IProgressMonitor() {
			private long fEndTime;

			public void beginTask(String name, int totalWork) {
				this.fEndTime = (System.currentTimeMillis() + timeout);
			}

			public boolean isCanceled() {
				return this.fEndTime <= System.currentTimeMillis();
			}

			public void done() {
			}

			public void internalWorked(double work) {
			}

			public void setCanceled(boolean value) {
			}

			public void setTaskName(String name) {
			}

			public void subTask(String name) {
			}

			public void worked(int work) {
			}
		};
	}

	private String[] getFavoriteStaticMembers() {
		String serializedFavorites = PreferenceConstants.getPreferenceStore()
				.getString("content_assist_favorite_static_members");
		if ((serializedFavorites != null) && (serializedFavorites.length() > 0)) {
			return serializedFavorites.split(";");
		}
		return new String[0];
	}



	public String getErrorMessage() {
		return this.fErrorMessage;
	}

	public void sessionStarted() {
	}

	public void sessionEnded() {
		this.fErrorMessage = null;
	}
}
