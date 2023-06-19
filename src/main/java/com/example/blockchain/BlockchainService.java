package com.example.blockchain;

import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.Transfer;
import org.web3j.tx.gas.DefaultGasProvider;
import org.web3j.utils.Convert;

import java.math.BigDecimal;
import java.math.BigInteger;

public class BlockchainService {

    private static final String PRIVATE_KEY = "3d58573fb943ffa5aeff74e23815ae6d1027e53f1fcd9239afb290386bd6c086";
    private static final String CONTRACT_ADDRESS = "0x99bbEC35bD8EC0fe5f3bB1728E87Ce41386B0b1a";

    private Web3j web3j;

    public BlockchainService() {
        // Conexión a Ganache
        this.web3j = Web3j.build(new HttpService("HTTP://127.0.0.1:7545"));
    }

    public void deposit(BigInteger amountInWei) throws Exception {
        // Cargamos las credenciales
        Credentials credentials = Credentials.create(PRIVATE_KEY);

        // Enviamos Ether al contrato
        Transfer.sendFunds(
                web3j,
                credentials,
                CONTRACT_ADDRESS,
                new BigDecimal(amountInWei),
                Convert.Unit.WEI
        ).send();
    }

    public BigInteger getBalance() throws Exception {
        // Cargamos el contrato
        Bank contract = Bank.load(
                CONTRACT_ADDRESS,
                web3j,
                Credentials.create(PRIVATE_KEY),
                new DefaultGasProvider()
        );

        // TODO: Resolver el problema con la llamada al método getBalance del contrato inteligente.
        // Actualmente, el tipo de retorno no coincide con lo esperado (TransactionReceipt en lugar de BigInteger).
        // Es necesario investigar y posiblemente ajustar el contrato inteligente o la forma en que se llama desde Java.

        // Devolver null como marcador de posición mientras el problema no esté resuelto.
        return null;
    }
}
