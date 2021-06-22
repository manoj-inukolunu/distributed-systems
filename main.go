package main

import (
	"distributed-systems/vectorclock"
	"fmt"
)

func main() {

	vClock := vectorclock.New(15, 8)
	vClock1 := vectorclock.New(15, 7)

	vClock.Event()
	for i := 0; i < 10; i++ {
		vClock.SendMessage()
	}

	vClock1.ReceiveMessage(vClock)

	vClock.PrintClock()
	fmt.Println()
	vClock1.PrintClock()

}
