package br.gov.caixa.sivit.config;

import net.minidev.json.JSONObject;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

public final class KeycloakRealmRoleConverter implements Converter<Jwt, Collection<GrantedAuthority>> {

    private final String SCOPE_PREFIX = "SCOPE_";
    private final String ROLE_PREFIX = "ROLE_";

    @SuppressWarnings("unchecked")
    public Collection<GrantedAuthority> convert(final Jwt jwt) {

        final Map<String, Object> realmAccess = (Map<String, Object>) jwt.getClaims().get("realm_access");

        Set<GrantedAuthority> authorities = ((List<String>) realmAccess.get("roles")).stream()
                .map(roleName -> ROLE_PREFIX + roleName)
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toSet());

        authorities.addAll(getScopes(jwt));
        authorities.addAll((getRolesSources(jwt)));

        return authorities;
    }

    private Collection<GrantedAuthority> getRolesSources(Jwt jwt) {
        final Map<String, Object> resources = (Map<String, Object>) jwt.getClaims().get("resource_access");
        List<GrantedAuthority> resourAccess = new ArrayList<>();
        for(Object value : resources.values()) {
            JSONObject resourceAccess = (JSONObject) value;
            resourAccess.addAll(((List<String>) resourceAccess.get("roles")).stream()
                    .map(roleName -> ROLE_PREFIX + roleName)
                    .map(SimpleGrantedAuthority::new)
                    .collect(Collectors.toList()));
        }
        return resourAccess;
    }

    private Collection<GrantedAuthority> getScopes(final Jwt jwt) {

        Object scope = jwt.getClaim("scope");
        if (scope instanceof String) {
            Collection<String> scopes = StringUtils.hasText((String)scope) ? Arrays.asList(((String)scope).split(" ")) : Collections.emptyList();
            return scopes
                    .stream()
                    .map(roleName -> SCOPE_PREFIX + roleName)
                    .map(SimpleGrantedAuthority::new)
                    .collect(Collectors.toList());

        }

        return Collections.emptyList();
    }
}
