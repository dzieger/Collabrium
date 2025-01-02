# Hardcoded User Logic (Dev Profile)

## Purpose
The hardcoded user logic was originally implemented for rapid prototyping and debugging in the development profile ('dev'). It provides predefined admin and user credentials without requiring database setup. Very useful for setting up JWT and Spring Security.

Later, the logic was transitioned to database-seeded users for :
- Consistency across devlopment and production environment
- Easier scalability as more features (eg., roles, permissions) were added.
- Reduced complexity in maintaining conditional logic for `dev` and `prod` profiles.

### Hardcoded User with Credentials
When the `dev` profile is active, the following users are available:

| Username | Password | Role         | Token Version |
|----------|----------|--------------|---------------|
| admin    | password | `ROLE_ADMIN` | 0             |
/user      / password / `ROLE_USER`  / 0             /

### How to Re-Enable
1. Uncomment the `loadDevUser` method in `AllUserDetailsService`.
2. Ensure the `dev` profile is active:
    ```properties
    spring.rpofiles.active=dev
```

## Edge Cases
1. **Token Version Mismatch**
    - Hardcoded users always use a static `tokenVersion` (eq., `0`). If hardcoded user is transitioned to a DB user, ensure token version is persisted.



