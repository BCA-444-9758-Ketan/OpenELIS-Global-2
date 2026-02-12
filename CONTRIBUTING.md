# Contributing to OpenELIS Global 2

The OpenELIS Global software is an open enterprise-level laboratory information
system built on open source web-based technologies that has been tailored for
low-and-middle income country public health laboratories.

The software serves as both an effective laboratory software solution and
business process framework. It supports the effective functioning of public
health laboratories for best laboratory practice and accreditation. And such a
great task calls for great minds just like you. Find out more ways of
contributing to this noble cause at
https://openelis-global.org/community/get-involved/

---

## Getting Started

### Prerequisites

Before setting up the project, make sure you have the following installed:

| Tool                                                          | Required Version | Notes                                |
| ------------------------------------------------------------- | ---------------- | ------------------------------------ |
| [Java (OpenJDK)](https://openjdk.org/install/)                | **21 LTS**       | Build will fail with older versions  |
| [Docker](https://docs.docker.com/engine/install/)             | Latest           |                                      |
| [Docker Compose](https://docs.docker.com/compose/install/)    | Latest           | Included with Docker Desktop         |
| [Maven](https://maven.apache.org/install.html)               | 3.8+             |                                      |
| [Node.js](https://nodejs.org/)                                | 16+              | For frontend development             |
| [Git](https://git-scm.com/)                                  | Latest           |                                      |

> **Tip:** Use [SDKMAN](https://sdkman.io/) to manage Java versions easily:
> `sdk install java 21.0.1-tem && sdk use java 21.0.1-tem`

### Setup Steps

If you have already forked and cloned the repository, follow these steps to
complete the setup:

1. **Initialize and build submodules**

   ```bash
   cd OpenELIS-Global-2
   git submodule update --init --recursive
   cd dataexport
   mvn clean install -DskipTests -Dmaven.test.skip=true
   cd ..
   ```

2. **Create your environment configuration file**

   ```bash
   cp .env.example .env
   ```

   Edit `.env` to customize settings for your environment if needed. See
   `.env.example` for documentation of each variable.

3. **Build the project**

   ```bash
   mvn clean install -DskipTests -Dmaven.test.skip=true
   ```

   > **Important:** Always use **both** `-DskipTests` and
   > `-Dmaven.test.skip=true` when skipping tests. Using only `-DskipTests`
   > will still run integration tests.

4. **Start the development containers**

   ```bash
   docker compose -f dev.docker-compose.yml up -d
   ```

5. **Install frontend dependencies** (for frontend development)

   ```bash
   cd frontend
   npm install
   cd ..
   ```

6. **Access the application**

   | Instance     | URL                                    |
   | ------------ | -------------------------------------- |
   | New React UI | https://localhost/                      |
   | Legacy UI    | https://localhost/api/OpenELIS-Global/  |

   Default development credentials are documented in the
   [README](./README.md#the-instances-can-be-accessed-at).

   > **Note:** Your browser will show a security warning because the
   > development environment uses a self-signed certificate. Click "Advanced"
   > and then "Proceed" to continue.

### Reflecting Local Changes

- **Frontend (React):** Changes are hot-reloaded automatically.
- **Backend (Java):** Rebuild and restart the container:

  ```bash
  mvn clean install -DskipTests -Dmaven.test.skip=true
  docker compose -f dev.docker-compose.yml up -d --no-deps --force-recreate oe.openelis.org
  ```

---

## Contribution Workflow

### 1. Find an Issue

- Browse [open issues](https://github.com/DIGI-UW/OpenELIS-Global-2/issues)
  on the upstream repository.
- Look for issues labeled
  [`good first issue`](https://github.com/DIGI-UW/OpenELIS-Global-2/labels/good%20first%20issue)
  for beginner-friendly tasks.
- Check the issue comments to make sure no one else is already working on it.

### 2. Get the Issue Assigned

- **Comment on the issue** to express your interest before starting work
  (e.g., "I'd like to work on this issue").
- **Wait for a maintainer** to confirm and assign the issue to you. This
  prevents duplicate efforts from multiple contributors.
- Do **not** submit a PR for an unassigned issue without prior discussion, as
  it may conflict with ongoing work or planned changes.

### 3. Create a Branch

Always create your feature branch from an up-to-date `develop` branch:

```bash
git checkout develop
git pull --rebase upstream develop
git checkout -b issue-<number>
```

If you haven't set up the upstream remote yet:

```bash
git remote add upstream https://github.com/DIGI-UW/OpenELIS-Global-2.git
```

### 4. Make Your Changes

- Keep changes focused on the issue at hand.
- Follow the project coding conventions and architecture (see
  [AGENTS.md](./AGENTS.md) for detailed guidelines).
- Use [React Intl](https://formatjs.io/docs/react-intl/) for all user-facing
  strings (no hardcoded English text).
- Use [Carbon Design System](https://carbondesignsystem.com/) components for
  UI changes (no Bootstrap or Tailwind).

### 5. Format Your Code

**Always run formatters before committing:**

```bash
# Backend (Java)
mvn spotless:apply

# Frontend (React)
cd frontend && npm run format && cd ..
```

### 6. Test Your Changes

```bash
# Run backend tests
mvn test

# Run frontend unit tests
cd frontend && npm test && cd ..

# Run individual E2E tests during development
cd frontend && npm run cy:spec "cypress/e2e/<test-file>.cy.js" && cd ..
```

### 7. Submit a Pull Request

- Target the **`develop`** branch (not `main`).
- Include the issue number in the PR title (e.g., `issue-123: Fix null check`).
- Include a link to the issue in the PR description.
- Attach screenshots for any UI changes (before and after).
- See [PULL_REQUEST_TIPS.md](./PULL_REQUEST_TIPS.md) for the full checklist.

---

## Good First Issues

Issues labeled
[`good first issue`](https://github.com/DIGI-UW/OpenELIS-Global-2/labels/good%20first%20issue)
are a great starting point for new contributors. These typically include:

- **Bug fixes** with clear error logs and reproduction steps
- **Test coverage improvements** for existing services and DAOs
- **Hardcoded string fixes** (replacing hardcoded text with internationalized
  messages)
- **Small refactors** to improve error handling or code quality

Browse the current list at:
https://github.com/DIGI-UW/OpenELIS-Global-2/labels/good%20first%20issue

---

## Code of Conduct

Please review and follow our
[Contributor Code of Conduct](./CODE_OF_CONDUCT.md).
