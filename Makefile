all:
	(cd poof-core; make $(MFLAGS) all)
	(cd poof-textui; make $(MFLAGS) all)

clean:
	(cd poof-core; make $(MFLAGS) clean)
	(cd poof-textui; make $(MFLAGS) clean)

install:
	(cd poof-core; make $(MFLAGS) install)
	(cd poof-textui; make $(MFLAGS) install)
