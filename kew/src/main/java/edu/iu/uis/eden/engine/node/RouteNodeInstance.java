/*
 * Copyright 2005-2006 The Kuali Foundation.
 * 
 * 
 * Licensed under the Educational Community License, Version 1.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 * http://www.opensource.org/licenses/ecl1.php
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package edu.iu.uis.eden.engine.node;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import edu.iu.uis.eden.KEWServiceLocator;
import edu.iu.uis.eden.doctype.DocumentType;
import edu.iu.uis.eden.routeheader.DocumentRouteHeaderValue;

/**
 * Represents a materialized instance of a {@link RouteNode} definition on a {@link DocumentRouteHeaderValue}.  Node instances
 * are generated by the engine using the {@link RouteNode} as a prototype and connected as a 
 * Directed Acyclic Graph.
 *
 * @author ewestfal
 */
public class RouteNodeInstance implements Serializable {
    
	private static final long serialVersionUID = 7183670062805580420L;
	
	private Long routeNodeInstanceId;
    private Long documentId;
    private Branch branch;
    private RouteNode routeNode;
    private boolean active = false;
    private boolean complete = false;
    private boolean initial = true;
    private RouteNodeInstance process;
    private List<RouteNodeInstance> nextNodeInstances = new ArrayList<RouteNodeInstance>();
    private List<RouteNodeInstance> previousNodeInstances = new ArrayList<RouteNodeInstance>();
    private List<NodeState> state = new ArrayList<NodeState>();
    	
    private Integer lockVerNbr;
    
    public boolean isActive() {
        return active;
    }
    public void setActive(boolean active) {
        this.active = active;
    }
    
    public boolean isComplete() {
        return complete;
    }
    public void setComplete(boolean complete) {
        this.complete = complete;
    }
    public Branch getBranch() {
        return branch;
    }
    public void setBranch(Branch branch) {
        this.branch = branch;
    }
    public RouteNode getRouteNode() {
        return routeNode;
    }
    public void setRouteNode(RouteNode node) {
        this.routeNode = node;
    }
    public Long getRouteNodeInstanceId() {
        return routeNodeInstanceId;
    }
    public void setRouteNodeInstanceId(Long routeNodeInstanceId) {
        this.routeNodeInstanceId = routeNodeInstanceId;
    }
    public Long getDocumentId() {
        return documentId;
    }
    public void setDocumentId(Long routeHeaderId) {
        this.documentId = routeHeaderId;
    }
    public List getNextNodeInstances() {
        return nextNodeInstances;
    }
    public RouteNodeInstance getNextNodeInstance(int index) {
    	while (getNextNodeInstances().size() <= index) {
    		nextNodeInstances.add(new RouteNodeInstance());
    	}
    	return (RouteNodeInstance) getNextNodeInstances().get(index);
    }
    public void setNextNodeInstances(List<RouteNodeInstance> nextNodeInstances) {
        this.nextNodeInstances = nextNodeInstances;
    }
    public List<RouteNodeInstance> getPreviousNodeInstances() {
        return previousNodeInstances;
    }
    public RouteNodeInstance getPreviousNodeInstance(int index) {
    	while (previousNodeInstances.size() <= index) {
    		previousNodeInstances.add(new RouteNodeInstance());
    	}
    	return (RouteNodeInstance) getPreviousNodeInstances().get(index);
    }
    public void setPreviousNodeInstances(List<RouteNodeInstance> previousNodeInstances) {
        this.previousNodeInstances = previousNodeInstances;
    }
    public boolean isInitial() {
        return initial;
    }
    public void setInitial(boolean initial) {
        this.initial = initial;
    }
    public List getState() {
        return state;
    }
    public void setState(List<NodeState> state) {
        this.state = state;
    }
    public RouteNodeInstance getProcess() {
		return process;
	}
	public void setProcess(RouteNodeInstance process) {
		this.process = process;
	}
	public Integer getLockVerNbr() {
        return lockVerNbr;
    }
    public void setLockVerNbr(Integer lockVerNbr) {
        this.lockVerNbr = lockVerNbr;
    }
    
    public NodeState getNodeState(String key) {
        for (Iterator iter = getState().iterator(); iter.hasNext();) {
            NodeState nodeState = (NodeState) iter.next();
            if (nodeState.getKey().equals(key)) {
                return nodeState;
            }
        }
        return null;
    }
    
    public void addNodeState(NodeState state) {
        this.state.add(state);
        state.setNodeInstance(this);
    }
    
    public void removeNodeState(String key) {
        for (Iterator iter = getState().iterator(); iter.hasNext();) {
            NodeState nodeState = (NodeState) iter.next();
            if (nodeState.getKey().equals(key)) {
                iter.remove();
                break;
            }
        }
    }
    
    public void addNextNodeInstance(RouteNodeInstance nextNodeInstance) {
        nextNodeInstances.add(nextNodeInstance);
        nextNodeInstance.getPreviousNodeInstances().add(this);
    }
    
    public void removeNextNodeInstance(RouteNodeInstance nextNodeInstance) {
        nextNodeInstances.remove(nextNodeInstance);
        nextNodeInstance.getPreviousNodeInstances().remove(this);
    }
    
    public void clearNextNodeInstances() {
        for (Iterator iterator = nextNodeInstances.iterator(); iterator.hasNext();) {
            RouteNodeInstance nextNodeInstance = (RouteNodeInstance) iterator.next();
            iterator.remove();
            nextNodeInstance.getPreviousNodeInstances().remove(this);
        }
    }
    
    public String getName() {
        return (getRouteNode() == null ? null : getRouteNode().getRouteNodeName());
    }
    
    public boolean isInProcess() {
        return getProcess() != null;
    }
    
    public DocumentType getDocumentType() {
        return KEWServiceLocator.getDocumentTypeService().findById(getDocumentId());
    }
    
    /*
     * methods used to display route node instances' data on documentoperation.jsp
     */
    
    public NodeState getState(int index){
    	while (state.size() <= index) {
            state.add(new NodeState());
        }
        return (NodeState) getState().get(index);
    }   

    public void populateState(List<NodeState> state) {
        this.state.addAll(state);
     }

}
