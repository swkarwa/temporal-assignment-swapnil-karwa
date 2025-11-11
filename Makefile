gw = ./gradlew

clean-and-build:
	$(gw) clean build

# order of execution for question-1

q1-run-worker-factory:
	$(gw) clean question-1:run-worker-factory

q1-run-work-flow:
	$(gw) clean question-1:run-workflow --info

q1-run-status-checker:
	$(gw) clean question-1:run-status-checker --info


# order of execution for question-2
q2-run-worker-factory:
	$(gw) clean question-2:q2-run-worker-factory --info

q2-run-workflow:
	$(gw) clean question-2:q2-run-workflow --info


#order of execution for question-3:
q3-run-starter:
	$(gw) clean question-3:q3-run-starter --info