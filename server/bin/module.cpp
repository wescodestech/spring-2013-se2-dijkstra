/*************************************************************************
 * module.cpp
 *
 * Created on January 31, 2013 by Matthew A. Crist for Team Dijkstra.
 * Course: Software Engineering II - ACL2 Email Client
 * Instructor: Dr. Rex Page
 * License: GNU GPL v2
 *
 * Purpose:
 * This class is designed to encapsulate the information that will be used
 * for designating modules for monitoring.
 *
 * Change Log:
 * 0.0.1		-	Initial conception of module.h
 */

#include "module.h"

using namespace std;

//////////////////////////////////////////////////////////////////////////
// Constructors / Destructor                                            //
//////////////////////////////////////////////////////////////////////////

/*
 * Default constructor for this class.
 */
Module::Module()
{
}	// end constructor

/*
 * Argumented constructor for this class.
 */
Module::Module(string name, string monitor, string invoke)
{
	this->name = name;
	this->monitor = monitor;
	this->invoke = invoke;
}	// end constructor

/*
 * Destructor for this class.
 */
Module::~Module()
{
}	// end destructor

//////////////////////////////////////////////////////////////////////////
// Mutators                                                             //
//////////////////////////////////////////////////////////////////////////

/*
 * Sets the name of this module.
 */
void Module::setName(string name)
{
	this->name = name;
}	// end function setName

/*
 * Sets the monitor for this module.
 */
void Module::setMonitor(string monitor)
{
	this->monitor = monitor;
}	// end function setMonitor

/*
 * Sets the invoke module for this module.
 */
void Module::setInvoke(string invoke)
{
	this->invoke = invoke;
}	// end function setInvoke

/*
 * Adds a dependency for this module.
 */
void Module::addDependency(Module* module)
{
	this->dependencies.push_back(module);
}	// end function addDependency

//////////////////////////////////////////////////////////////////////////
// Accessors                                                            //
//////////////////////////////////////////////////////////////////////////

/*
 * Acquires the name of this module.
 */
string Module::getName()
{
	return this->name;
}	// end function getName

/*
 * Acquires the monitor for this module.
 */
string Module::getMonitor()
{
	return this->monitor;
}	// end function getMonitor

/*
 * Acquires the module to invoke.
 */
string Module::getInvoke()
{
	return this->invoke;
}	// end function getInvoke

/*
 * Acquires the dependencies of this module.
 */
vector<Module*> Module::getDependencies()
{
	return this->dependencies;
}	// end function getDependencies
