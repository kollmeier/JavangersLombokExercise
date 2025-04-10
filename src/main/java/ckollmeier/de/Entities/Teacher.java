package ckollmeier.de.Entities;

/**
 * Diese Klasse repräsentiert einen Lehrer mit seinen Basisinformationen.
 * @param id Eindeutige Kennung des Lehrers.
 * @param name Vollständiger Name des Lehrers.
 * @param address Wohnadresse des Lehrers.
 */
public record Teacher(
        String id,
        String name,
        String address
) {
}
