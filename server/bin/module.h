#ifndef DIJKSTRA_MODULE_H
#define DIJKSTRA_MODULE_H

/*************************************************************************
 * module.h
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

#include <string>
#include <vector>

using namespace std;

class Module
{
	private:
		// The qualified name for this module
		string name;
		// The folder that will be monitored for invocation.
		string monitor;
		// The module that will be invoked
		string invoke;
		// Dependencies for this module
      vector<Module*> dependencies;

	public:
		// Constructors/Destructor
		Module();
		Module(string, string, string);
		~Module();

		// Mutators
		void setName(string);
		void setMonitor(string);
		void setInvoke(string);
		void addDependency(Module*);
	
		// Accessors
		string getName();
		string getMonitor();
		string getInvoke();
		vector<Module*> getDependencies();
};	// end class Module

#endif
