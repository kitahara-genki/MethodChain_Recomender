package kwic;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.beans.PropertyChangeSupport;
import java.io.*;

/** 
 * Key Word in Context
 */

public class KWIC {

	protected PropertyChangeSupport pcs;

	public KWIC() { 
		pcs = (new PropertyChangeSupport(this)); 
	}

	/** 
	 * Required for part (b) of this lab.
	 * Accessor for the {@link PropertyChangeSuppport} 
	 */

	public PropertyChangeSupport getPCS() { return pcs; }

	/** 
	 * Convenient interface, accepts a standrd Java {@link String}
	 * @param s String to be added
	 */
	public void addPhrase(String s) {
		addPhrase(new Phrase(s));
	}
	
	/**
	 * Add each line in the file as a phrase.
	 * For each line in the file, call {@link addPhrase(String)} to
	 *   add the line as a phrase.
	 * @param file the file whose lines should be loaded as phrases
	 */
	public void addPhrases(File file) {
		//
		// FILL ME IN after you have everything else working
		//
	}

	/** 
	 * For each {@link Word} in the {@link Phrase}, 
	 * add the {@link Word}
	 * to the association.
	 * Use reduction to {@link #forceAssoc(Word, Phrase)}.
	 * @param p Phrase to be added
	 */
	public void addPhrase(Phrase p) {
		for (Word w : p.getWords()) {
			forceAssoc(w, p);			
		}
		pcs.firePropertyChange("Phrase Added",false, true);
	}


	/** For each word in the {@link Phrase}, delete the association between
      the word and the phrase.
      Use reduction to {@link #dropAssoc(Word, Phrase)}.
	 */
	public void deletePhrase(Phrase p) {
		for (Word w : p.getWords()) {
			dropAssoc(w,p);
		}
		pcs.firePropertyChange("Phrase Deleted",false,true);
	}

	/** Force a mapping between the speicified {@link Word} and {@link Phrase} */
	public void forceAssoc(Word w, Phrase p) {
		//
		// FILL ME IN
		// Leave the following line as the last line of this method
		//
		pcs.firePropertyChange("Phrase Added",false,true);
	}


	/** 
	 * Drop the association between the 
	 * specified {@link Word} and {@link Phrase}, if any
	 */
	public void dropAssoc(Word w, Phrase p) {
		//
		// FILL ME IN
		// Leave the following line as the last line of this method
		//
		pcs.firePropertyChange("Phrase Deleted",false,true);
	}


	/** Return a Set that provides each {@link Phrase} associated with
    the specified {@link Word}.
	 */
	public Set<Phrase> getPhrases(Word w) {
		//
		// FILL ME IN
		// This method should never return null
		//
		return null;
	}
	
	/** 
	 * Drop a word completely from the KWIC 
	 * 
	 * @param w Word to be dropped
	 */
	public void deleteWord(Word w) {
		//
		// FILL ME IN
		// Leave the following line as the last line
		//
		pcs.firePropertyChange("Word Deleted",false,true);
	}

	/** Rerturn a Set of all words */
	public Set<Word> getWords() {
		//
		// FILL ME IN
		// This method should never return null
		return null;
	}
}
