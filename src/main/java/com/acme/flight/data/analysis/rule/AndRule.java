package com.acme.flight.data.analysis.rule;

import com.acme.flight.data.analysis.model.ArrivalInfo;

public class AndRule implements Rule {

	private Rule firstRule;
	private Rule secondRule;

	public AndRule(Rule firstRule, Rule secondRule) {
		this.firstRule = firstRule;
		this.secondRule = secondRule;
	}

	@Override
	public boolean accepts(ArrivalInfo arrival) {
		return firstRule.accepts(arrival) && secondRule.accepts(arrival);
	}

}
