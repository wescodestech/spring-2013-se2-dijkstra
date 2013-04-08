package lib;

/******************************************************************************
  * Module.java
  * Created by Matthew A. Crist on March 26, 2013.
  *
  * The class defines the basic characteristics of a module and the requirements
  * that will be enforced when such moduel requires network transmission of 
  * information.
  *
  * CHANGE LOG: 
  * ---------------------------------------------------------------------------
  * 2013-03-26	-	Initial conception of this file.
  *
  *****************************************************************************/

import java.io.IOException;
import java.net.ServerSocket;

public class Module {
	/** The name of the module. */
	private String name;
	/** The program/script to be invoked upon connectivity. */
	private String invocation;
	/** The port in which to listen for connectivity. */
	private String port;

	/**
     * Default constructor that takes no arguments and initializes this 
     * module's parameters to default values.
     */
	public Module() {
		this.name       = "Unknown Module";
		this.invocation = "./";
		this.port       = "0";
	}	// end default constructor

	/**
     * Argumented constructor for this class that will set the name, port
     * and script to be invoked upon successfully connection with the client.
     */
	public Module(String name, String invocation, String port)	{
		this.name       = name;
		this.invocation = invocation;
		this.port       = port;
	}	// end argumented constructor

	/**
     * Sets the name for this module.
     */
	public void setName(String name)	{
		this.name = name;
	}	// end method setName

	/**
	  * Sets the invocation script for this module.
     */
	public void setInvocation(String invocation)	{
		this.invocation = invocation;
	}	// end method setInvocation

	/**
	  * Sets the port for this module.
     */
	public void setPort(String port) {
		this.port = port;
	}	// end method setPort

	/**
     * Acquires the name of this module.
     */
	public String getName()	{
		return this.name;
	}	// end method getName

	/**
	  * Acquires the script to be invoked by this server on connectivity.
     */
	public String getInvocation()	{
		return this.invocation;
	}	// end method getInvocation

	/**
     * Acquires the port in which the server should be listening for this 
     * module.
     */
	public String getPort() {
		return this.port;
	}	// end method getPort
}	// end class module
