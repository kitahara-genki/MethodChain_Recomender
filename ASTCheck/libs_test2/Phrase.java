package kwic;
import java.util.HashSet;
import java.util.Set;
import java.util.StringTokenizer;

/**
 * Represent a phrase.
 *
 */
public class Phrase {

	final protected String phrase;

	public Phrase(String s){
		phrase = s;
	}

	/** 
	 * Provide the words of a phrase.  
	 * Each word may have to be cleaned up:  
	 * punctuation removed, put into lower case
	 */

	public Set<Word> getWords() {
		// FILL ME IN
		// Use StringTokenizer to break the
		//  phrase into words
		// Return a Set of such words
		// This should never return null
		return null;
	}

	/** The behavior of this lab depends on how you view this method.
      Are two phrases the same because they have the same words?
      Or are they the same because they are string-equlivalent.
      <UL>
       <LI> What song,  Is that Becky
       <LI> What song is that, Becky
      </UL>
      The above phrases have the same words but are different strings.
	 */
	public boolean equals(Object o ) {
		//
		//  FIX like you did for Word
		return super.equals(o);
	}

	/** This method must also be properly defined, or else your {@link HashSet}
      structure won't operate properly.
	 */
	public int hashCode() {
		//  FIX like you did for Word
		return super.hashCode();
	}

	/** Filter the supplied {@link String} (which is the String of
      a {@link Phrase} presumably) into a canonical form
      for subsequent matching.
      The actual filtering depends on what you consider to be
      insignificant in terms of matching.  
      <UL> <LI> If punctuation is
      irrelevant, remove puncutation.
           <LI> If case does not matter, than convert to lower (or upper)
	        case.
      </UL>
	 */
	protected static String cleanUp(String s){
		// FIX ME
		// Don't just return s, but return a cleaned up version of s
		//   as described above
		return s;
	}

	public String toString(){
		return phrase;
	}

}
