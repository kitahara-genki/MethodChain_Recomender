/*
 *  This software may only be used by you under license from AT&T Corp.
 *  ("AT&T").  A copy of AT&T's Source Code Agreement is available at
 *  AT&T's Internet website having the URL:
 *  <http://www.research.att.com/sw/tools/graphviz/license/source.html>
 *  If you received this software without first entering into a license
 *  with AT&T, you have an infringing copy of this software and cannot use
 *  it without violating AT&T's intellectual property rights.
 */

package att.grappa;

import java.io.*;
import java.util.*;

/**
 * This abstract class is the root class for the
 * <A HREF="att.grappa.Node.html">Node</A>,
 * <A HREF="att.grappa.Edge.html">Edge</A>,
 * <A HREF="att.grappa.Subgraph.html">Subgraph</A> and
 * <A HREF="att.grappa.Graph.html">Graph</A> classes.
 * It is the basis for describing the graph elements.
 *
 * @version 1.2, ; Copyright 1996 - 2010 by AT&T Corp.
 * @author  <a href="mailto:john@research.att.com">John Mocenigo</a>, <a href="http://www.research.att.com">Research @ AT&T Labs</a>
 */
public abstract class Element
    implements att.grappa.GrappaConstants
{
    // the containing graph and the parent subgraph element
    private Graph graph = null;
    private Subgraph subgraph = null;

    // used for checking whether we've been here for dfs/bfs
    long visastamp = -1L;

    /*
     * These (deleteCalled and busy) used by reserve/release/setDelete to
     * queue a delete request during a critical function (e.g., drawing) and
     * to define the start and end of that critical function.
     */
    private boolean deleteCalled = false;
    private boolean busy = false;

    /*
     * A look-up table that allows a user (via setUserAttributeType) to
     * associate a String-to-Object and vice versa translation for
     * attributes they may supply.
     */
    private static HashMap<String,Integer> userAttributeTypeMap = null;

    /**
     * A general-purpose object not used by Grappa and intended for
     * application writers to attach whatever they want to an Element
     * without the need for extending the class.
     */
    public Object object;

    /**
     * Indicates whether this element should be considered visible or not.
     * If not visible, it will not be drawn and will not be selected by a
     * mouse click (if the supplied selection methods are used). Note that
     * there is redundancy here with the <i>invis</i> component of the
     * <i>style</i> attribute. This value must be true and <i>invis</i>
     * must be false for the element to be visible.
     */
    public boolean visible = true;

    /**
     * Indicates whether this element should be considered selectable or not.
     * The default is true.
     */
    public boolean selectable = true;

    /**
     * Indicates indicates line width for element (for nodes or subgraphs,
     * it is the outline width, when applicable).
     * The default is 0 (single pixel).
     */
    public int linewidth = 0;

    /**
     * Indicates whether only the list of attributes found in the
     * PRINTLIST_ATTR should be printed.
     * The default is false.
     */
    public static boolean usePrintList        		= false;

    /**
     * A convenience variable, not used by Grappa, but available to keep
     * track of or otherwise mark graph elements when traversing a graph.
     */
    public int counter = 0;

    /**
     * Determines the type of highlighting to apply, if any, when drawing.
     * Currently recognized choices are SELECTION_MASK and DELETION_MASK. 
     */
    public int highlight = 0;

    // identification
    private Long idKey = null;
    String name = null;

    // attributes
    HashMap<String,Attribute> attributes = null;

    // attributes
    // speedy cache to eliminate many redundant objs...chas
    private static HashMap<Integer,Set<String>> AttrOfInterestCache = new HashMap<Integer,Set<String>>();
    private static HashMap<Set<String>,String[]> AttrOfInterestCacheR = new HashMap<Set<String>,String[]>();
    Set<String> attrsOfInterest = null;

    // the Shape for drawing
    GrappaNexus grappaNexus = null;

    /**
     * Boolean to indicate if all of this element's attributes should
     * be printed. Either this flag or the <TT>elementPrintAllAttributes</TT>
     * can turn on printing of all attributes.
     *
     * @see Subgraph#printSubgraph
     * @see Grappa#elementPrintAllAttributes
     */
    public boolean printAllAttributes = false;
    /**
     * Boolean to indicate if the default attributes associated with
     * this element should be printed. Naturally, this option only
     * is effective if the element is a subgraph.
     *
     * @see Subgraph#printSubgraph
     * @see Grappa#elementPrintDefaultAttributes
     */
    public boolean printDefaultAttributes = false;

    // canonical name
    String canonName = null;

    /**
     * Element constructor needed only during init phase of
     * <A HREF="att.grappa.Graph.html">Graph</A> class.
     * Since the Element class is abstact, it cannot be instantiated directly.
     */
    protected Element() {
	// needed due to Graph init (a special case of Subgraph)
    }
  
    /**
     * Element constructor used during init phase of the
     * <A HREF="att.grappa.Node.html">Node</A>,
     * <A HREF="att.grappa.Edge.html">Edge</A> and
     * <A HREF="att.grappa.Subgraph.html">Subgraph</A> classes.
     * Since the Element class is abstact, it cannot be instantiated directly.
     *
     * @param type the type of the element (Grappa.NODE, Grappa.EDGE or Grappa.SUBGRAPH).
     * @param subg the subgraph containing this element.
     *
     * @see GrappaConstants#NODE
     * @see GrappaConstants#EDGE
     * @see GrappaConstants#SUBGRAPH
     */
    protected Element(int type, Subgraph subg) {
    	//super();
    	setSubgraph(subg);
    	setGraph(subg.getGraph());
    	setIdKey(type);
    	getGraph().addIdMapping(this);

    	elementAttrsOfInterest();
    }

    // set the attributes of interest to all elements
    static String[] ElementAttributesOfInterest = new String[] {
    	COLOR_ATTR,FONTCOLOR_ATTR,FONTNAME_ATTR,FONTSIZE_ATTR,FONTSTYLE_ATTR,LABEL_ATTR
    } ;
    
    private void elementAttrsOfInterest() {
    	attrOfInterest( ElementAttributesOfInterest ) ;
    }
    
    /**
     * Get the type of this Element. Useful for distinguishing Element objects.
     *
     * @return the appropriate class variable constant
     *
     * @see GrappaConstants#NODE
     * @see GrappaConstants#EDGE
     * @see GrappaConstants#SUBGRAPH
     */
    public abstract int getType();

    /**
     * Check if this Element is a node. Overridden in Node to return true.
     *
     * @return false, unless overridden.
     *
     * @see Node#isNode()
     */
	public boolean isNode() {
		return (false);
	}

    /**
     * Check if this Element is an edge. Overridden in Edge to return true.
     *
     * @return false, unless overridden.
     *
     * @see Edge#isEdge()
     */
	public boolean isEdge() {
		return (false);
	}

    /**
     * Check if this Element is a subgraph. Overridden in Subgraph to return true.
     *
     * @return false, unless overridden.
     *
     * @see Subgraph#isSubgraph()
     */
    public boolean isSubgraph() {
    	return(false);
    }

    /**
     * Intended to be a subclass-specific name generating method.
     * Used by edges and when nodes or graphs are created without
     * an explicit name.
     * Note that graphs and nodes should also have a setName that
     * takes an explicit name as an argument.
     */
    abstract void setName();

    /**
     * Get the name of this Element.
     *
     * @return the name of the element.
     */
    public String getName() {
    	return(name);
    }

    /**
     * Check if this Element can be reserved, otherwise queue request
     *
     * @return true, if successfully reserved.
     */
    boolean reserve() {
    	return setReserved(true, false);
    }

    /**
     * Release the reservation on this Element, if any.
     */
    void release() {
    	setReserved(false, false);
    }

    /**
     * Queue or unqueue a delete request.
     *
     * @return true, unless a delete request has already been queued
     *         for this Element.
     */
    boolean setDelete(boolean delete) {
	return setReserved(delete, true);
    }

    // handle the reserve/release/setDelete requests
    private synchronized boolean setReserved(boolean state, boolean isDelete) {
    	if(isDelete) {
    		if(state) {
    			deleteCalled = true;
    			if(busy) {
    				return(false);
    			} else {
    				return(busy = true);
    			}
    		} else {
    			deleteCalled = busy = false;
    			return(true);
    		}
    	} else if(state) {
    		if(deleteCalled) return(false);
    		return(busy = true);
    	} else {
    		if(!deleteCalled) {
    			busy = false;
    		} else {
    			busy = deleteCalled = false;
    			delete();
    		}
    		return(true);
    	}
    }

    /**
     * fairly hacked code to allow sharing of attribute Sets.  Attribute Sets are
     * composed through the addition of attributes at each level of a Object hierarchy.
     * The Set should be marked readonly somehow....TODO.  Anyway, there are only a handful
     * of Class types needed, so there really is no need to replicate a HashSet for each.
     * So, trade off some time in finding the Set with the time needed to create a Set and
     * time needed to garbage collect, as well as memory usage.
     * @param names
     */
    protected void attrOfInterest(String[] names) {
    	Arrays.sort(names);
    	int hash = Arrays.deepHashCode( names ) ;
    	if( attrsOfInterest == null ) {
    		attrsOfInterest = AttrOfInterestCache.get(hash);
    		if( attrsOfInterest == null ) {
    			attrsOfInterest = new HashSet<String>() ;
    			//SubgraphAttributesOfInterest(minbox,minsize,label,lp,style)をハッシュセットに追加
    			for( String name : names ) {
    				attrsOfInterest.add( name ) ;
    			}
    			AttrOfInterestCache.put(hash,attrsOfInterest);
    			AttrOfInterestCacheR.put(attrsOfInterest,names);
    		}
    	} else {
    		//
    		// Need to merge the current attrs with the incoming set.
    		// come up with the new set.  This will be called due to the expanded
    		// attr set from subclassed types.
    		//
    		String[] oldNames = AttrOfInterestCacheR.get(attrsOfInterest);
    		String[] newNames = new String[ oldNames.length + names.length ] ;
    		System.arraycopy( oldNames, 0, newNames, 0, oldNames.length );
    		System.arraycopy( names, 0, newNames, oldNames.length, names.length ) ;
    		Arrays.sort( newNames ) ;
    		hash = Arrays.deepHashCode(newNames);
    		attrsOfInterest = AttrOfInterestCache.get(hash);
    		if( attrsOfInterest == null ) {
    			attrsOfInterest = new HashSet<String>() ;
    			for( String name : newNames ) {
    				attrsOfInterest.add( name ) ;
    			}
    			AttrOfInterestCache.put(hash,attrsOfInterest);
    			AttrOfInterestCacheR.put(attrsOfInterest,newNames);
    		}
    	}
    	if ( attrsOfInterest != null ) {
    		if(grappaNexus != null) {
    			for( String name : names ) {
    				Attribute attr = getAttribute(name);
    				if(attr != null) {
    					attr.addListener(grappaNexus);
    				}
    			}
    		}
    	}
    }
    
    /**
     * Add the name of an attribute of interest to this element
     *
     * @param name the name of the attribute
     */
    /*
    protected void attrOfInterest(String name) {
    	if(name == null || isOfInterest(name)) return;
    	if(attrsOfInterest == null) {
    		attrsOfInterest = new HashSet<String>();
    	}
    	attrsOfInterest.add(name);
    	if(grappaNexus != null) {
    		Attribute attr = getAttribute(name);
    		if(attr != null) {
    			attr.addObserver(grappaNexus);
    		}
    	}
    }
    **/

    /**
     * Remove the name of an attribute of interest to this object
     *
     * @param name the name of the attribute
     */
    /**
    protected void attrNotOfInterest(String name) {
    	if(name == null || !isOfInterest(name)) return;
    	if(grappaNexus != null) {
    		Attribute attr = getAttribute(name);
    		if(attr != null) attr.deleteObserver(grappaNexus);
    	}
    	attrsOfInterest.remove(name);
    } **/

    /**
     * Provide an iterator of the names of the attributes of interest.
     *
     * @return an iterator of attribute names that are of interest
     */
    public Iterator<String> listAttrsOfInterest() {
    	if(attrsOfInterest == null) return Grappa.emptyStringIterator.iterator();
    	return attrsOfInterest.iterator();
    }

    /**
     * Check if the name of an attribute of interest to this object
     *
     * @param name the name of the attribute
     * @return true when the name is of interest
     */
    public boolean isOfInterest(String name) {
    	if(name == null || attrsOfInterest == null) return false;
    	return attrsOfInterest.contains(name);
    }
  
    /**
     * Sets or creates an attribute for this element from the attribute supplied.
     * The storage key is the attribute name.  If the value portion of the
     * supplied attribute is null, then the attribute will be removed from the
     * element.
     *
     * @param attr the attribute from which to set the element's attribute.
     * @return the value of the (local) attribute previously stored
     *         under the same name
     */
    public Object setAttribute(Attribute attr) {
	if(attr == null) {
	    return null;
	}
	return setAttribute(attr.getName(),attr.getValue());
    }
  
    /**
     * Sets or creates an attribute for this element from the supplied
     * arguments. The storage key is the attribute name.  If the value
     * argument is null, then the attribute will be removed from the element.
     *
     * @param name the attribute name
     * @param value the attribute value
     * @return the value of the (local) attribute previously stored
     *         under the same name
     */
    public Object setAttribute(String name, Object value) {
	if(attributes == null) {
	    attributes = new HashMap<String,Attribute>();
	}
	if(name == null) {
	    throw new IllegalArgumentException("cannot set an attribute using a null name");
	}

	Object oldValue = null;
	Attribute crntAttr =  getLocalAttribute(name);
	if(crntAttr == null) {
	    if(value == null) {
		return null;
	    } else if(value instanceof String && ((String)value).trim().length() == 0 && Attribute.attributeType(getType(),name) != STRING_TYPE) {
		return null;
	    }
	    attributes.put(name,(crntAttr = new Attribute(getType(),name,value)));
	    if(grappaNexus != null && isOfInterest(name)) {
		crntAttr.addListener(grappaNexus);
	    }
	} else {
	    oldValue = crntAttr.getValue();
	    if(value == null) {
		//System.err.println("direct removal of ("+name+","+value+") from "+getName());
		removeAttribute(name);
		return oldValue;
	    } else if(value instanceof String && ((String)value).trim().length() == 0 && Attribute.attributeType(getType(),name) != STRING_TYPE) {
		//System.err.println("removal of ("+name+","+value+") from "+getName());
		removeAttribute(name);
		return oldValue;
	    } else {
		crntAttr.setValue(value);
	    }
	}
	if(crntAttr.hasChanged()) {
	    crntAttr.notifyListeners(new Long(System.currentTimeMillis()));
	}
	return oldValue;
    }

  
    /*
     * Removes the named attribute from the (local) attribute table and
     * applies the default attribute (if any)
     *
     * @param name the name of the attribute to be removed.
     * @return the default attribute pair for this attribute.
     */
    private Attribute removeAttribute(String name) {
    	if(name == null) return null;
    	Attribute dfltAttr =  getDefaultAttribute(name);
    	Attribute attr = null;
    	if(attributes != null) attr = (Attribute)attributes.remove(name);
    	if(attr == null) return dfltAttr;
    	if(dfltAttr == null) {
    		attr.setValue("");
    	}
    	attr.setChanged();
    	attr.notifyListeners(new Object[] { dfltAttr, new Long(System.currentTimeMillis()) });
    	return dfltAttr;
    }
  
    /**
     * Sets or creates a default attribute for this element type within the
     * containing subgraph of this element from the supplied arguments.
     * The storage key is the attribute name.
     * If the value argument is null, then the
     * attribute will be removed from the subgraph.
     *
     * @param name the attribute name
     * @param value the attribute value
     * @return the value of the (default) attribute previously stored
     *         under the same name
     */
    public Object setDefaultAttribute(String name, Object value) {
    	return setDefaultAttribute(getType(),name,value);
    }
  
    /**
     * Sets or creates a default attribute of the specified type within the
     * containing subgraph of this element from the supplied arguments.
     * The storage key is the attribute name.
     * If the value argument is null, then the
     * attribute will be removed from the subgraph.
     *
     * @param type the default attribute type
     * @param name the attribute name
     * @param value the attribute value
     * @return the value of the (default) attribute previously stored
     *         under the same name
     */
    public Object setDefaultAttribute(int type, String name, Object value) {
    	Object oldValue = null;
    	Subgraph subg = getSubgraph();
    	switch(type) {
    	case Grappa.NODE:
    		oldValue = subg.setNodeAttribute(name,value);
    		break;
    	case Grappa.EDGE:
    		oldValue = subg.setEdgeAttribute(name,value);
    		break;
    	case Grappa.SUBGRAPH:
    		// ignore subg == null (i.e., root subgraph case) 
    		if(subg != null) {
    			oldValue = subg.setAttribute(name,value);
    		}
    		break;
    	}
    	return oldValue;
    }

    /**
     * Sets or creates a default attribute for this element type within the
     * containing subgraph of this element from the supplied arguments.
     * The storage key is the attribute name.
     * If the value portion of the supplied attribute is null, then the
     * attribute will be removed from the subgraph.
     *
     * @param attr the attribute to which the default should be set
     * @return the value of the (default) attribute previously stored
     *         under the same name
     */
    public Object setDefaultAttribute(Attribute attr) {
	return setDefaultAttribute(getType(),attr);
    }

    /**
     * Sets or creates a default attribute of the specified type within the
     * containing subgraph of this element from the supplied arguments.
     * The storage key is the attribute name.
     * If the value portion of the supplied attribute is null, then the
     * attribute will be removed from the subgraph.
     *
     * @param type the default attribute type
     * @param attr the attribute to which the default should be set
     * @return the value of the (default) attribute previously stored
     *         under the same name
     */
    public Object setDefaultAttribute(int type, Attribute attr) {
    	if(attr == null) return null;
    	Object oldValue = null;
    	Subgraph subg = getSubgraph();
    	switch(type) {
    	case Grappa.NODE:
    		oldValue = subg.setNodeAttribute(attr);
    		break;
    	case Grappa.EDGE:
    		oldValue = subg.setEdgeAttribute(attr);
    		break;
    	case Grappa.SUBGRAPH:
    		// ignore subg == null (i.e., root subgraph case) 
    		if(subg != null) {
    			oldValue = subg.setAttribute(attr);
    		}
    		break;
    	}
    	return oldValue;
    }
  
    /**
     * Gets an Iterator of the keys for this Element's local attributes.
     *
     * @return an Iterator of String objects
     */
  
    public Iterator<String>  getLocalAttributeKeys() {
    	if(attributes == null) {
    		return Grappa.emptyStringIterator.iterator();
    	}
    	return( attributes.keySet().iterator() );
    }

    /**
     * Get an Iterator of the Attribute objects for this Element.
     *
     * @return an Iterator of the (local) Attribute objects.
     */
    public Iterator<Attribute>  getLocalAttributePairs() {
    	if(attributes == null) {
    		return Grappa.emptyAttributeIterator.iterator();
    	}
    	return( attributes.values().iterator() );
    }

    /**
     * Get an Iterator of all attribute pairs for this element.
     *
     * @return an Iterator of local and default Attribute objects for this element.
     */
    public Iterator<Attribute> getAttributePairs() {
    	HashMap<String,Attribute> pairs = null;
    	Attribute attr = null;

    	Iterator<Attribute> enm = getLocalAttributePairs();
    	if(enm.hasNext()) pairs = new HashMap<String,Attribute>(32);
    	while(enm.hasNext()) {
    		attr = enm.next();
    		pairs.put(attr.getName(),attr);
    	}

    	switch(getType()) {
    	case Grappa.NODE:
    		enm = getSubgraph().getNodeAttributePairs();
    		break;
    	case Grappa.EDGE:
    		enm = getSubgraph().getEdgeAttributePairs();
    		break;
    	case Grappa.SUBGRAPH:
    		enm = getLocalAttributePairs();
    		break;
    	}

    	if(pairs != null) {
    		while(enm.hasNext()) {
    			attr = enm.next();
    			if(!pairs.containsKey(attr.getName())) {
    				pairs.put(attr.getName(),attr);
    			}
    		}
    		return pairs.values().iterator();
    	}

    	return enm;
    }

    /**
     * Get only the corresponding local attribute for the specified key.  A local attribute is
     * one associated directly with this element as opposed to a subgraph
     * ancestor.
     * 属性のハッシュマップが空ならnull、それ以外ならkeyを表す属性を返す。
     * @param key the search key for the corresponding attribute.
     *
     * @return the local Attribute object matching the key or null.
     */
    public Attribute getLocalAttribute(String key) {
    	if(attributes == null) return(null);
    	return((Attribute)(attributes.get(key)));
    }

    /**
     * Get only the corresponding local attribute for the specified key if
     * it is not inherited from the parent, otherwise return null. Sometimes
     * a local attribute will be set, but it will be a pointer to the
     * parent value. This method distinguishes that case.
     *
     * @param key the search key for the corresponding attribute.
     *
     * @return the local Attribute object matching the key or null if it is not defined or it is a pointer to the parent attribute..
     */
    public Attribute getThisAttribute(String key) {
    	Attribute attr;
    	Subgraph sg;
    	if(attributes == null) return(null);
    	if((attr = (Attribute)(attributes.get(key))) == null) return(null);
    	if((sg = getSubgraph()) == null) return(attr);
    	if(attr == sg.getAttribute(key)) return(null);
    	return(attr);
    }

    /**
     * Get only the value of the corresponding local attribute for the
     * specified key if the attribute is not inherited from the parent,
     * otherwise return null. Sometimes a local attribute will be set,
     * but it will be simply a pointer to the parent attribute.
     * This method distinguishes that case.
     *
     * @param key the search key for the corresponding attribute.
     *
     * @return the value of the local Attribute object matching the key or null if it is not defined or it is a pointer to the parent attribute..
     */
    public Object getThisAttributeValue(String key) {
	Attribute attr = getThisAttribute(key);
	if(attr == null) return(null);
	return(attr.getValue());
    }

    /**
     * Get the corresponding default attribute for the specified type and key.
     *
     * @param type the type of the default attribute
     * @param key the search key for the corresponding attribute.
     * @return the value of the default Attribute object matching the key or null.
     */
    public Attribute getDefaultAttribute(int type, String key) {
	Attribute value = null;
	Subgraph sg = null;

	if(isSubgraph()) sg = (Subgraph)this;
	else sg = getSubgraph();

	if(sg == null) {
	    // unattached, so try global attributes
	    return(Graph.getGlobalAttribute(type,key));
	}

	switch(type) {
	case Grappa.NODE:
	    value = sg.getNodeAttribute(key);
	    break;
	case Grappa.EDGE:
	    value = sg.getEdgeAttribute(key);
	    break;
	case Grappa.SUBGRAPH:
	    value = sg.getLocalAttribute(key);
	    break;
	}
	return(value);
    }

    /**
     * Get the default attribute of this element for the specified key.
     *
     * @param key the search key for the corresponding attribute.
     * @return the value of the default Attribute object matching the key or null.
     */
    public Attribute getDefaultAttribute(String key) {
	return getDefaultAttribute(getType(),key);
    }

    /**
     * Get the Attribute of this Element for the specified key.
     * Search first local, then default attributes until a match is found.
     *
     * @param key the search key for the attribute.
     * @return the corresponding Attribute object or null.
     */
    public Attribute getAttribute(String key) {
	Attribute attr = null;
    
	if((attr = getLocalAttribute(key)) == null) {
	    attr = getDefaultAttribute(key);
	}
	return(attr);
    }

    /**
     * Get the Attribute value of this Element for the specified key.
     * Search first local, then default attributes until a match is found.
     * ノード、エッジに対して引数の文字列のステータス値を取ってくる。keyにはposや
     * labelなどの文字列が入る
     * @param key the search key for the attribute.
     * @return the corresponding attribute value or null.
     */
    public Object getAttributeValue(String key) {
	Object value = null;

	Attribute attr = getAttribute(key);
	if(attr != null) {
	    value = attr.getValue();
	}
	return(value);
    }

    /**
     * Checks to see if this element has an Attribute matching the key
     *
     * @param key the search key for the attribute.
     * @return true if there is a matching attribute, false otherwise.
     */
    public boolean hasAttributeForKey(String key) {
	if(getAttribute(key) == null) return(false);
	return(true);
    }

    /**
     * Get the Graph of this Element.
     *
     * @return the containing graph object.
     */
    public Graph getGraph() {
	return(graph);
    }

    /**
     * Get the containing Subgraph of this Element.
     *
     * @return the parent subgraph object.
     */
    public Subgraph getSubgraph() {
	return(subgraph);
    }

    /**
     * Set the containing graph for this element.
     *
     * @param graph the overall graph that contains this element.
     */
    void setGraph(Graph graph) {
	this.graph = graph;
    }

    /**
     * Set the parent subgraph for this element.
     *
     * @param subgraph the parent subgraph that contains this element.
     */
    public void setSubgraph(Subgraph subgraph) {
	if(this.subgraph != null && this.subgraph != subgraph) {
	    switch(this.getType()) {
	    case Grappa.NODE:
		this.subgraph.removeNode(((Node)this).getName());
		subgraph.addNode((Node)this);
		break;
	    case Grappa.EDGE:
		this.subgraph.removeEdge(((Edge)this).getName());
		subgraph.addEdge((Edge)this);
		break;
	    case Grappa.SUBGRAPH:
		this.subgraph.removeSubgraph(((Subgraph)this).getName());
		subgraph.addSubgraph((Subgraph)this);
		break;
	    }
	}
	if(this.subgraph != subgraph) {
	    if(this.subgraph != null && this.subgraph.grappaNexus != null)
		this.subgraph.clearBBox();
	    if(subgraph != null && subgraph.grappaNexus != null)
		subgraph.clearBBox();
	}
	this.subgraph = subgraph;
    }

    protected void clearBBox() { // formerly resetBBox
	if(grappaNexus != null) grappaNexus.bbox = null;
	Subgraph prnt = getSubgraph();
	while(prnt != null) {
	    if(prnt.grappaNexus != null) {
		prnt.grappaNexus.bbox = null;
	    }
	    prnt = prnt.getSubgraph();
	}
    }

    /**
     * Get the ID number of this Element.
     *
     * @return the id number of this element.
     */
    public int getId() {
	return (int)((getIdKey().longValue())>>Grappa.TYPES_SHIFT);
    }

    /**
     * Get the ID of this Element as a Long object.
     *
     * @return the id object of this element.
     */
    public Long getIdKey() {
	return(idKey);
    }

    /**
     * Sets the id key of this element
     */
    protected void setIdKey(int type) {
	idKey = Graph.idMapKey(type,getGraph().nextId(type));
    }

    /**
     * Print a description of this element to the given print stream.
     *
     * @param out the print stream for output.
     */
    public void printElement(PrintWriter out) {
	String indent = new String(getGraph().getIndent());

	if(Grappa.printVisibleOnly && (!visible || grappaNexus.style.invis))
	    return;
    
	out.print(indent + toString());
	getGraph().incrementIndent();
	printAttributes(out,indent);
	getGraph().decrementIndent();
	out.println();
    }

    /*
     * Print attributes to given stream.  A square open bracket prefix and
     * closed bracket suffix enclose the attributes, but are printed only if
     * there are any attributes to print.  The supplied indent determines the
     * indentation of the final bracket (it is assumed the element name has
     * already printed to the output stream.
     *
     * @param out the print stream for output.
     * @param outerIndent the indent to use for the prefix and suffix.
     */
    private void  printAttributes(PrintWriter out, String outerIndent) {
	String indent = new String(getGraph().getIndent());
	String prefix = " [";
	String suffix = Grappa.NEW_LINE + outerIndent + "];";
	Attribute attr;
	String key;
	boolean first = true;
	// thanks to Ginny Travers (bbn.com) for suggesting the printlist feature
	HashMap printlist = null;

	if(Grappa.usePrintList || usePrintList) {
	    printlist = (HashMap)getAttributeValue(PRINTLIST_ATTR);
	}

	Iterator<Attribute> attrs = null;
	if(Grappa.elementPrintAllAttributes || printAllAttributes) {
	    attrs = getAttributePairs();
	} else if(attributes != null && !attributes.isEmpty()) {
	    attrs = attributes.values().iterator();
	}
	if(attrs != null) {
	    while(attrs.hasNext()) {
		attr = (Attribute)(attrs.next());
		key = attr.getName();
		if(printlist != null && printlist.get(key) == null) continue;
		if(attr != null && (Grappa.elementPrintAllAttributes || printAllAttributes || !attr.equalsValue(getDefaultAttribute(key)))) {
		    if(first) {
			first = false;
			out.println(prefix);
		    } else {
			out.println(",");
		    }
		    out.print(indent + key + " = " + canonString(attr.getStringValue()));
		}
	    }
	}
	if(getGraph().filterMode && isEdge()) {
	    if(first) {
		first = false;
		out.println(prefix);
	    } else {
		out.println(",");
	    }
	    out.print(indent + "__nAmE__ = " + canonString(getName()));
	}
	if(!first) {
	    out.print(suffix);
	}
    }

    /**
     * Get the String rendition of the element.
     *
     * @return the string rendition of the element, quoted as needed.
     */
    public String toString() {
	if(canonName == null) {
	    canonName = canonString(name);
	}
	return(canonName);
    }

    /**
     * Canonicalizes the supplied string for output.
     *
     * @param input the string to be quoted, possibly.
     * @return the input string, possibly enclosed in double quotes and
     *         with internal double quotes protected.
     */
    // essentially the agstrcanon function from libgraph (by S. C. North)
    public static String canonString(String input) {
    	int len;

    	if(input == null || (len = input.length()) == 0) {
    		return("\"\"");
    	}

    	StringBuilder strbuf = new StringBuilder(len + 8);
    	char[] array = input.toCharArray();
    	boolean has_special = false;
    	boolean isHTML=false;
    	String  tmpstr;

    	for(char c : array ) {
    		if(c == '"') {
    			strbuf.append('\\');
    			has_special = true;
    		} else if(!has_special) {
    			if(!Lexer.id_char(c)) {
    				has_special = true;
    			}
    		}
    		strbuf.append(c);
    	}
    	// Based ona suggestion by Martin Bierkoch to
    	// keep Grappa from corrupting HTML-like graphviz labels
    	if((tmpstr = strbuf.toString().trim()).startsWith("<") && tmpstr.endsWith(">"))
    		isHTML=true;

    	// annoying, but necessary kludge to make libgraph parser happy
    	if(!has_special && len <= 8) {
    		String low = input.toLowerCase();
    		if(
    				low.equals("node") || low.equals("edge") || low.equals("graph") ||
    				low.equals("digraph") || low.equals("subgraph") || low.equals("strict")
    		) {
    			has_special = true;
    		}
    	}
    	if(has_special&&(!isHTML)) {
    		strbuf.append('"');
    		strbuf.insert(0,'"');
    	}
    	return(strbuf.toString());
    }


    /**
     * Provides the element type as a string.
     *
     * @param elemType an integer value representing an element type
     * @param uplow set true to indicate the return value should be
     *              leading-capitalized, otherwise lower-case is returned
     * @return the meaning of the element type in english
     */
    public final static String typeString(int elemType, boolean uplow) {
	String type = null;

	switch(elemType) {
	case NODE:
	    type = uplow ? "Node" : "node";
	    break;
	case EDGE:
	    type = uplow ? "Edge" : "edge";
	    break;
	case SUBGRAPH:
	    type = uplow ? "Subgraph" : "subgraph";
	    break;
	case SYSTEM:
	    type = uplow ? PKG_UPLOW : PKG_LOWER;
	    break;
	default:
	    type = null;
	}

	return(type);
    }

    /**
     * Canonicalizes the supplied string for look-up.
     * NOTE: Not currently used by Grappa.
     *
     * @param input the string to be canonicalized.
     * @return the input string, with non-alphanumerics 
     *              removed and alphabetics are converted to lower-case.
     */
    public static String canonValue(String input) {
    	if(input == null) return null;
    	char[] array = input.toCharArray();
    	int len = 0;
    	boolean allDigits = true;
    	for( char c : array ) {
    		if(Character.isUpperCase(c)) {
    			array[len++] = Character.toLowerCase(c);
    			allDigits = false;
    		} else if(Character.isLowerCase(c)) {
    			array[len++] = c;
    			allDigits = false;
    		} else if(Character.isDigit(c)) {
    			array[len++] = c;
    		}
    	}
    	if(len == 0 || allDigits) return null;
    	return new String(array,0,len);
    }

    /**
     * Boolean inicating if a delete request has been received by this element.
     */
    boolean deleteCalled() {
	return deleteCalled;
    }

    /**
     * Method for deleting an element.
     * Clears element references from graph tables and frees up space explicitly.
     * @see Graph#reset()
     */
    public final boolean delete() {
    	if(!setDelete(true)) return(false);
    	String name = getName();
    	if(attributes != null && grappaNexus != null) {
    		for( Attribute attr : attributes.values() ) {
    			attr.removeListener(grappaNexus);
    		}
    	}
    	Subgraph prnt = null;
    	// account for bounding box change due to deletion
    	if(grappaNexus != null) grappaNexus.bbox = null;
    	prnt = getSubgraph();
    	while(prnt != null) {
    		if(prnt.grappaNexus != null) prnt.grappaNexus.bbox = null;
    		prnt = prnt.getSubgraph();
    	}
    	switch(getType()) {
    	case Grappa.NODE:
    		Edge[] edges = ((Node)this).edgeElementsAsArray();
    		for( Edge edge : edges ) {
    			prnt = edge.getSubgraph();
    			while(prnt != null) {
    				if(prnt.grappaNexus != null) prnt.grappaNexus.bbox = null;
    				prnt = prnt.getSubgraph();
    			}
    			edge.delete();
    		}
    		getSubgraph().removeNode(name);
    		break;
    	case Grappa.EDGE:
    		((Edge)this).getTail().removeEdge(((Edge)this),false);
    		((Edge)this).getHead().removeEdge(((Edge)this),true);
    		getSubgraph().removeEdge(name);
    		break;
    	case Grappa.SUBGRAPH:
    		Node[] nodes = ((Subgraph)this).nodeElementsAsArray();
    		for( Node node : nodes) {
    			node.delete();
    		}
    		edges = ((Subgraph)this).edgeElementsAsArray();
    		for( Edge edge : edges ) {
    			edge.delete();
    		}
    		Subgraph[] subgraphs = ((Subgraph)this).subgraphElementsAsArray();
    		for( Subgraph subgraph : subgraphs ) {
    			subgraph.delete();
    		}
    		if(getSubgraph() != null) getSubgraph().removeSubgraph(name);
    		break;
    	}
    	getGraph().removeIdMapping(this);
    	if(grappaNexus != null) {
    		grappaNexus.element = null;
    		grappaNexus = null;
    	}
    	return(true);
    }

    /**
     * Tags the element with the supplied string. 
     * 
     * @param tag the tag to associate with this Element.
     */
    public void addTag(String tag) {
	Attribute attr;
	HashMap tags;

	if(tag == null || tag.indexOf(',') >= 0) {
	    throw new RuntimeException("tag value null or contains a comma (" + tag + ")");
	}

	if((attr = getLocalAttribute(TAG_ATTR)) == null) {
	    attr = new Attribute(getType(),TAG_ATTR,new HashMap());
	    setAttribute(attr);
	    attr = getAttribute(TAG_ATTR); // needed
	}
	tags = (HashMap)(attr.getValue());

	tags.put(tag,tag);
	// if it becomes desireable to retain the original order, we
	// could always use the value in the following (instead of
	// what is done above) to reconstruct the original order
	// (Note that no code makes use of the value at this point,
	// so that would all have to be added in printAttributes, for
	// example)
	// tags.put(tag,new Long(System.currentTimeMillis()));
    }

    /**
     * Check if this Element has the supplied tag either locally or as a default.
     *
     * @param tag tag value to be searched for
     * @return true, if this Element contains the supplied tag
     */
    public boolean hasTag(String tag) {
    	Attribute attr;
    	HashMap tags;

    	if(tag == null || tag.indexOf(',') >= 0) {
    		throw new RuntimeException("tag value null or contains a comma (" + tag + ")");
    	}

    	if((attr = getLocalAttribute(TAG_ATTR)) == null) {
    		return(hasDefaultTag(tag));
    	}
    	tags = (HashMap)(attr.getValue());
    	if(tags == null || tags.size() == 0) return false;
    	return(tags.containsKey(tag));
    }

    /**
     * Check if this Element has the supplied tag locally.
     *
     * @param tag tag value to be searched for
     * @return true, if this Element contains the supplied tag
     */
    public boolean hasLocalTag(String tag) {
    	Attribute attr;
    	HashMap tags;
    	if((attr = getLocalAttribute(TAG_ATTR)) == null) return false;
    	tags = (HashMap)(attr.getValue());
    	if(tags == null || tags.size() == 0) return false;
    	return(tags.containsKey(tag));
    }

    /**
     * Check if this Element has the supplied tag as a default tag.
     *
     * @param tag tag value to be searched for
     * @return true, if this Element has the supplied tag as a default tag
     */
    public boolean hasDefaultTag(String tag) {
    	Attribute attr;
    	HashMap tags;

    	if((attr = getDefaultAttribute(TAG_ATTR)) == null) return false;
    	tags = (HashMap)(attr.getValue());
    	if(tags == null || tags.size() == 0) return false;
    	return(tags.containsKey(tag));
    }

    /**
     * Check if this Element is tagged at all either locally or with a default.
     *
     * @return true, if this Element is tagged at all
     */
    public boolean hasTags() {
    	Attribute attr;
    	HashMap tags;

    	if((attr = getLocalAttribute(TAG_ATTR)) == null) {
    		return(hasDefaultTags());
    	}
    	tags = (HashMap)(attr.getValue());
    	if(tags == null || tags.size() == 0) return false;
    	return true;
    }

    /**
     * Check if this Element is tagged at all locally.
     *
     * @return true, if this Element is tagged locally
     */
    public boolean hasLocalTags() {
    	Attribute attr;
    	HashMap tags;
    	if((attr = getLocalAttribute(TAG_ATTR)) == null) return false;
    	tags = (HashMap)(attr.getValue());
    	if(tags == null || tags.size() == 0) return false;
    	return true;
    }

    /**
     * Check if this Element has any default tags at all.
     *
     * @return true, if this Element has any default tags
     */
    public boolean hasDefaultTags() {
    	Attribute attr;
    	HashMap tags;

    	if((attr = getDefaultAttribute(TAG_ATTR)) == null) return false;
    	tags = (HashMap)(attr.getValue());
    	if(tags == null || tags.size() == 0) return false;
    	return true;
    }

    /**
     * Removes all tags locally associated with this element.
     */
    public void removeTags() {
    	Attribute attr;
    	HashMap tags;
    	if((attr = getLocalAttribute(TAG_ATTR)) == null) return;
    	tags = (HashMap)(attr.getValue());
    	if(tags == null || tags.size() == 0) return;
    	tags.clear();
    }

    /**
     * Removes the specified tag locally from this element.
     *
     * @param tag the tag value to remove
     */
    public void removeTag(String tag) {
    	Attribute attr;
    	HashMap tags;
    	if((attr = getLocalAttribute(TAG_ATTR)) == null) return;
    	tags = (HashMap)(attr.getValue());
    	if(tags == null || tags.size() == 0) return;
    	tags.remove(tag);
    }

    /**
     * Sets the conversion type of a user-defined attribute.
     * Unless provided for a specific attribute name, the attribute value
     * will only be treated as a string. When provided, the string value
     * of the attribute will be converted to the given type and vice
     * versa.
     *
     * @param attrname the attribute name
     * @param attrtype the attribute type
     *
     * @return the previous type associated with this attribute name
     */
    public static int setUserAttributeType(String attrname, int attrtype) {
    	int oldtype = _NO_TYPE;
    	Integer type = null;

    	if(attrname == null || attrname.trim().length() == 0) {
    		throw new IllegalArgumentException("supplied attribute name should be non-null and contain some non-blank characters");
    	}

    	if(attrtype < 0) {
    		type = new Integer(attrtype);
    	} else {
    		switch(attrtype) {
    		case _NO_TYPE:
    		default:
    			// ignore
    			break;
    		case BOX_TYPE:
    		case COLOR_TYPE:
    		case DOUBLE_TYPE:
    		case FONTSTYLE_TYPE:
    		case INTEGER_TYPE:
    		case LINE_TYPE:
    		case POINT_TYPE:
    		case SHAPE_TYPE:
    		case SIZE_TYPE:
    		case STRING_TYPE:
    		case STYLE_TYPE:
    			type = new Integer(attrtype);
    			break;
    		}
    	}

    	if(type == null) {
    		throw new IllegalArgumentException("supplied type for attribute (" + attrname + ") should be less than zero or a recognized type value");
    	}

    	if(userAttributeTypeMap == null) {
    		userAttributeTypeMap = new HashMap<String,Integer>();
    	}
    	Integer old = userAttributeTypeMap.get(attrname);
    	if(old != null) {
    		oldtype = old.intValue();
    	}
    	userAttributeTypeMap.put(attrname,type);

    	return(oldtype);
    }

    /**
     * Returns the attribute conversion type for the supplied attribute name.
     *
     * @param attrname the attribute name
     * @return the currently associated attribute type
     */
    public static int attributeType(String attrname) {
    	int convtype = -1;
    	int hashCode;

    	if(attrname != null) {
    		hashCode = attrname.hashCode();
// 与えられた文字列(左辺)の識別を行う。これにより○○ = ××の右辺(××)をどうやって得るか判別できる
// convtypeの値により、属性として与えられるものの型も決まる。LINE_TYPEならGrappaLineが返る   		
    		if(hashCode == BBOX_HASH && attrname.equals(BBOX_ATTR)) {
    			convtype = BOX_TYPE;
    		} else if(hashCode == COLOR_HASH && attrname.equals(COLOR_ATTR)) {
    			convtype = COLOR_TYPE;
    		} else if(hashCode == FILLCOLOR_HASH && attrname.equals(FILLCOLOR_ATTR)) {
    			convtype = COLOR_TYPE;
    		} else if(hashCode == FONTCOLOR_HASH && attrname.equals(FONTCOLOR_ATTR)) {
    			convtype = COLOR_TYPE;
    		} else if(hashCode == FONTSIZE_HASH && attrname.equals(FONTSIZE_ATTR)) {
    			//convtype = INTEGER_TYPE;
    			convtype = DOUBLE_TYPE;
    		} else if(hashCode == FONTSTYLE_HASH && attrname.equals(FONTSTYLE_ATTR)) {
    			convtype = FONTSTYLE_TYPE;
    		} else if(hashCode == HEIGHT_HASH && attrname.equals(HEIGHT_ATTR)) {
    			convtype = DOUBLE_TYPE;
    		} else if(hashCode == LABEL_HASH && attrname.equals(LABEL_ATTR)) {
    			convtype = STRING_TYPE;
    		} else if(hashCode == LP_HASH && attrname.equals(LP_ATTR)) {
    			convtype = POINT_TYPE;
    		} else if(hashCode == PATCH_HASH && attrname.equals(PATCH_ATTR)) {
    			convtype = DOUBLE_TYPE;
    		} else if(hashCode == PRINTLIST_HASH && attrname.equals(PRINTLIST_ATTR)) {
    			convtype = HASHMAP_TYPE;
    		} else if(hashCode == STYLE_HASH && attrname.equals(STYLE_ATTR)) {
    			convtype = STYLE_TYPE;
    		} else if(hashCode == TAG_HASH && attrname.equals(TAG_ATTR)) {
    			convtype = HASHMAP_TYPE;
    		} else if(hashCode == WIDTH_HASH && attrname.equals(WIDTH_ATTR)) {
    			convtype = DOUBLE_TYPE;
    		}  
    		// 追加-----------------------------------------------------------------------
    		else if(hashCode == ARROWHEAD_HASH && attrname.equals(ARROWHEAD_ATTR)) {
    			//convtype = ARROWHEAD_TYPE;
    			convtype = STRING_TYPE;
    		}
    		else if(hashCode == ARROWTAIL_HASH && attrname.equals(ARROWTAIL_ATTR)) {
    			//convtype = ARROWTAIL_TYPE;
    			convtype = STRING_TYPE;
    		}
    		//----------------------------------------------------------------------------
    		
    		else if(userAttributeTypeMap != null) {
    			Integer usertype = (userAttributeTypeMap.get(attrname));
    			if(usertype == null) {
    				convtype = STRING_TYPE;
    			} else {
    				convtype = usertype.intValue();
    			}
    		} else {
    			convtype = STRING_TYPE;
    		}
    	}

    	return(convtype);
    }

    
    /**
     * Creates and populates the GrappaNexus object for this element.
     * The GrappaNexus object provides bounding and drawing information
     * for the element based on the element's attributes.
     */
    public final void buildShape() {
    	if(grappaNexus == null) {
    		grappaNexus = new GrappaNexus(this);
    		Attribute attr = null;
    		for( String sAttr : attrsOfInterest ) {
    			attr = getAttribute(sAttr);
    			if(attr != null) {
    				//attr.addObserver(grappaNexus);
    				attr.addListener( grappaNexus ) ;
    			}
    		}
    	}
    }

    /**
     * Returns the GrappaNexus object associated with this element.
     */
    public GrappaNexus getGrappaNexus() {
    	if(grappaNexus == null) {
    		buildShape();
    	}
    	return(grappaNexus);
    }

    /**
     * Performs a breadth-first or a depth-first search starting at this Element.
     *
     * @param steps when negative, the search is exhaustive; otherwise the search stops after the number of steps indicated
     *
     * @return a Vector of Vector, the ith element of which gives the search results for step i. Reading the vector in increasing order gives breadth-first search results, while using decreasing order gives depth-first results.
     */
    public java.util.List bdfs(int steps) {
    	ArrayList<Element> input;
    	ArrayList<List<Element>> layers;

    	input = new ArrayList<Element>(1);
    	input.add(this);

    	layers = new ArrayList<List<Element>>();

    	synchronized(getGraph()) {
    		doBDFS(getType(), steps, System.currentTimeMillis(), 0, input, layers);
    	}

    	return(layers);
    }

    private static void doBDFS(int type, int depth, long stamp, int level, ArrayList<Element> inbox, ArrayList<List<Element>> layers) {
    	Element elem;
    	Subgraph subg;
    	Edge edge;
    	int sz ;
    	ArrayList<Element> input;

    	if((sz = inbox.size()) == 0)
    		return;

    	layers.add(inbox);

    	level++;

    	if(depth >= 0 && level > depth)
    		return;

    	input = new ArrayList<Element>();

    	for(int i=0; i<sz; i++) {

    		elem = (Element)inbox.get(i);

    		if (type == SUBGRAPH) {

    			//stack.addElement(elem);

    			if(depth < 0 || level <= depth) {
    				Iterator<Subgraph> enm = ((Subgraph)elem).subgraphElements();
    				while(enm.hasNext()) {
    					subg = (Subgraph)(enm.next());
    					if (subg.visastamp != stamp) {
    						input.add(subg);
    						subg.visastamp = stamp;
    					}
    				}
    			}


    		} else if (type == NODE) {

    			//stack.addElement(elem);

    			if(depth < 0 || level <= depth) {
    				Iterator<Edge> enm = ((Node)elem).outEdgeElements();
    				while(enm.hasNext()) {
    					edge = (Edge)(enm.next());
    					if (edge.goesForward()) {
    						if (edge.getHead().visastamp != stamp) {
    							input.add(edge.getHead());
    							edge.getHead().visastamp = stamp;
    						}
    					}
    				}
    				enm = ((Node)elem).inEdgeElements();
    				while(enm.hasNext()) {
    					edge = (Edge)(enm.next());
    					if (edge.goesReverse()) {
    						if (edge.getTail().visastamp != stamp) {
    							input.add(edge.getTail());
    							edge.getTail().visastamp = stamp;
    						}
    					}
    				}
    			}

    		} else { // type == EDGE

    			//stack.addElement(elem);

    			if(depth < 0 || level <= depth) {
    				if (((Edge)elem).goesForward()) {
    					Iterator<Edge> enm = ((Edge)elem).getHead().outEdgeElements();
    					while(enm.hasNext()) {
    						edge = (Edge)(enm.next());
    						if (edge.goesForward()) {
    							if (edge.visastamp != stamp) {
    								input.add(edge);
    								edge.visastamp = stamp;
    							}
    						}
    					}
    					enm = ((Edge)elem).getHead().inEdgeElements();
    					while(enm.hasNext()) {
    						edge = (Edge)(enm.next());
    						if (edge.goesReverse()) {
    							if (edge.visastamp != stamp) {
    								input.add(edge);
    								edge.visastamp = stamp;
    							}
    						}
    					}
    				}
    				if (((Edge)elem).goesReverse()) {
    					Iterator<Edge>  enm = ((Edge)elem).getTail().outEdgeElements();
    					while(enm.hasNext()) {
    						edge = (Edge)(enm.next());
    						if (edge.goesForward()) {
    							if (edge.visastamp != stamp) {
    								input.add(edge);
    								edge.visastamp = stamp;
    							}
    						}
    					}
    					enm = ((Edge)elem).getTail().inEdgeElements();
    					while(enm.hasNext()) {
    						edge = (Edge)(enm.next());
    						if (edge.goesReverse()) {
    							if (edge.visastamp != stamp) {
    								input.add(edge);
    								edge.visastamp = stamp;
    							}
    						}
    					}
    				}
    			}
    		}
    	}

    	if(input.size() > 0)
    		doBDFS(type, depth, stamp, level, input, layers);
    }

    //
    // Start PatchWork stuff
    //

    private double patchSize = 0;

    double getPatchSize() {
    	return(patchSize);
    }

    void setPatchSize(double val) {
	patchSize = val;
    }

    private java.awt.geom.Rectangle2D.Double patch = null;

    java.awt.geom.Rectangle2D.Double getPatch() {
	return(patch);
    }

    void setPatch(java.awt.geom.Rectangle2D.Double p) {
	if(p == null)
	    patch = p;
	else if(patch == null)
	    patch = new GrappaBox(p.getX(), p.getY(), p.getWidth(), p.getHeight());
	else
	    patch.setRect(p.getX(), p.getY(), p.getWidth(), p.getHeight());
    }
    //矩形を用意する
    void setPatch(double x, double y, double w, double h) {
	if(patch == null)
	    patch = new GrappaBox(x, y, w, h);
	else
	    patch.setRect(x, y, w, h);
    }

    //
    // End PatchWork stuff
    //
}
