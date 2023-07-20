package se.bjurr.gitchangelog.internal.settings;

import static java.util.Objects.requireNonNull;

import java.io.Serializable;
import java.util.Optional;
import org.eclipse.jgit.lib.ObjectId;
import se.bjurr.gitchangelog.api.InclusivenessStrategy;
import se.bjurr.gitchangelog.api.exceptions.GitChangelogRepositoryException;
import se.bjurr.gitchangelog.internal.git.GitRepo;
import se.bjurr.gitchangelog.internal.git.ObjectIdBoundary;

/**
 * @author Réda Housni Alaoui
 */
public class RevisionBoundary implements Serializable {

	private static final long serialVersionUID = 1628515977948507257L;

	private final String revision;
	private final InclusivenessStrategy inclusivenessStrategy;

	private RevisionBoundary(String revision, InclusivenessStrategy inclusivenessStrategy) {
		this.revision = requireNonNull(revision);
		this.inclusivenessStrategy = requireNonNull(inclusivenessStrategy);
	}

	public static Optional<RevisionBoundary> parse(String revision, InclusivenessStrategy inclusivenessStrategy) {
		if (revision == null || revision.trim().isEmpty()) {
			return Optional.empty();
		}
		return Optional.of(new RevisionBoundary(revision.trim(), inclusivenessStrategy));
	}

	public static RevisionBoundary parseOrFail(String revision, InclusivenessStrategy inclusivenessStrategy) {
		return parse(revision, inclusivenessStrategy).orElseThrow(() -> new IllegalArgumentException(String.format("Parsing failed for revision '%s' and inclusivenessStrategy '%s'", revision, inclusivenessStrategy)));
	}

	public Optional<ObjectIdBoundary> findObjectId(final GitRepo gitRepo) throws GitChangelogRepositoryException {
		Optional<ObjectId> objectId = gitRepo.findRef(revision);
		if (!objectId.isPresent()) {
			objectId = Optional.ofNullable(gitRepo.getCommit(revision));
		}
		return objectId.map(id -> new ObjectIdBoundary(id, inclusivenessStrategy));
	}

	public String getRevision() {
		return revision;
	}

	public InclusivenessStrategy getInclusivenessStrategy() {
		return inclusivenessStrategy;
	}
}