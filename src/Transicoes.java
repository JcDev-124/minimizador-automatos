

class Transicoes {
    private String origin;
    private String destiny;
    private String symbol;
	
    
    
	public Transicoes(String origin, String destiny, String symbol) {
		super();
		this.origin = origin;
		this.destiny = destiny;
		this.symbol = symbol;
	}


	@Override
	public String toString() {
		return "Transicoes [origin=" + origin + " destiny=" + destiny + " symbol=" + symbol + "]";
	}


	public String getOrigin() {
		return origin;
	}


	public void setOrigin(String origin) {
		this.origin = origin;
	}


	public String getDestiny() {
		return destiny;
	}


	public void setDestiny(String destiny) {
		this.destiny = destiny;
	}


	public String getSymbol() {
		return symbol;
	}


	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}

    
}
