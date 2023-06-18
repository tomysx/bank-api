package com.example.apibank.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Entity

/*Evitamos un bucle infinito de serilización JSON al tratar de recuperar los movimientos de una wallet y con esta forma
conseguimos incluir en la respuesta de la petición una mayor información*/
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Wallet {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private BigDecimal balance;

    @ManyToOne
    private AppUser user;

    // Lista de transferencias entrantes y salientes asociadas a una wallet

    @OneToMany(mappedBy = "fromWallet", cascade = CascadeType.ALL)
    private Set<Transfer> outgoingTransfers = new HashSet<>();

    @OneToMany(mappedBy = "toWallet", cascade = CascadeType.ALL)
    private Set<Transfer> incomingTransfers = new HashSet<>();


    public Wallet() {

    }

    public Wallet(Long id, BigDecimal balance, AppUser user) {
        this.id = id;
        this.balance = balance;
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public AppUser getUser() {
        return user;
    }

    public void setUser(AppUser user) {
        this.user = user;
    }

    public Set<Transfer> getOutgoingTransfers() {
        return outgoingTransfers;
    }

    public void setOutgoingTransfers(Set<Transfer> outgoingTransfers) {
        this.outgoingTransfers = outgoingTransfers;
    }

    public Set<Transfer> getIncomingTransfers() {
        return incomingTransfers;
    }

    public void setIncomingTransfers(Set<Transfer> incomingTransfers) {
        this.incomingTransfers = incomingTransfers;
    }
}
