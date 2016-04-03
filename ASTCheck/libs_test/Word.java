package kwic;

/** Represents the original and matching forms of a word.  
 * You must implement 
 * {@link Object#hashCode()} correctly as well as
 * {@link Object#equals(Object)} 
 * for this to work.
 */

public class Word {


	/** Represent a word of a {@link Phrase}
	 * @param w The original word
	 */
	public Word(String w){
	}

	/**
	 * The word used for matching is the original word run throgh
	 * the WordCanonical filter.
	 * @return the form of the word used for matching.
	 * 
	 */
	public String getMatchWord() {
		//
		// FILL ME IN
		// This should never return null
		return null;
	}

	/**
	 * 
	 * @return the original word
	 */
	public String getOriginalWord() {
		//
		// FILL ME IN
		// This should never return null
		return null;
	}

	/** 
	 * You must implement this right, see lab writeup notes.
	 * 
	 * This is commented out so you can have eclipse generate
	 * a skeleton of it for you.
	 */
//	public int hashCode() { 
//		
//	}


	/**
	 * You must implement this so that two words equal each
	 * other if their matched forms equal each other.
	 * You can let eclipse generate this method automatically,
	 * but you have to modify the resulting code to get the
	 * desired effect.
	 * 
	 * This method is commented out so you can have eclipse generate
	 * a skeleton of it for you.
	 */
//	public boolean equals(Object o) {
//	}

	/**
	 * @return the word and its matching form, if different
	 */
	public String toString(){
		if (getOriginalWord().equals(getMatchWord()))
			return getOriginalWord();
		else
			return getOriginalWord() + " --> " + getMatchWord();
	}

}
