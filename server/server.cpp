#include <dirent.h>
#include <iostream>
#include <map>
#include <pthread.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <sys/types.h>
#include <unistd.h>

#include "bin/module.h"

using namespace std;

void* cycleModules(void*);
string* getNextToken(string&);

map<string, Module*> modules;

int main()
{
	Module* module = new Module("server.user.register", "/home/cristma/Projects/Software Engineering II/trunk/server/incoming/user/register", "test.lisp");
	modules.insert(pair<string, Module*>(module->getName(), module));

	pthread_t tId;
	pthread_attr_t attributes;
	pthread_attr_init(&attributes);
	pthread_create(&tId, &attributes, cycleModules, NULL);
	pthread_join(tId, NULL);

	cout << "System exiting..." << endl;
	return 0;
}	// end function main

void *cycleModules(void *param)
{
	for(map<string, Module*>::iterator itty = modules.begin(); itty != modules.end(); ++itty)
	{
		struct dirent *l_file       = NULL;
		       DIR    *l_hDirectory = NULL;
		const  char   *l_dLoc       = itty->second->getMonitor().c_str();

		// Attempt to open the monitored directory
		l_hDirectory = opendir(l_dLoc);
	
		// See if we were successful.
		if(l_hDirectory == NULL)
		{
			fprintf(stderr, "Directory does not for module %s.  Unable to monitor!\n", itty->first.c_str());
			continue;
		}	// end if
		else
		{
			while(l_file = readdir(l_hDirectory))
			{
				// Filter the . and .. options.
				string l_ignore_1 = ".";
				string l_ignore_2 = "..";
				int l_filter_1 = strcmp(l_file->d_name, l_ignore_1.c_str());
				int l_filter_2 = strcmp(l_file->d_name, l_ignore_2.c_str());
				
				if(l_filter_1 != 0 && l_filter_2 != 0)
				{
					fprintf(stdout, "File located: %s\n", l_file->d_name);
					
					pid_t pid = fork();

					if(pid < 0)
						fprintf(stderr, "Error executing invoke.\n");
					else if(pid == 0)
					{
						//waitpid(-1, NULL, 0);
						execlp("/home/cristma/Projects/Software Engineering II/server/monitors/invoke_acl2.sh", "", NULL);
					}
					else
					{
						// Wait
						//waitpid(-1, NULL, 0);
						cout << "System exiting...\n";
					}
				}	// end if
			}	// end while

			// Done with the directory, close it.
			closedir(l_hDirectory);
		}	// end else
	}	// end for

	// Exit this thread.
	pthread_exit(0);
}	// end function monitorDirectories

string* getNextToken(string& xml)
{
	
}	// end function getNextToken
