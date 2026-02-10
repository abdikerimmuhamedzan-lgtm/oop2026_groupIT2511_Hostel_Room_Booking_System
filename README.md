oop2026_groupIT2511_Hostel_Room_Booking_System
## Team Members:
1. Abdikerim Muhamedzhan
2. Kabdrahmanova Zarina

## Project Description
Project Progress Report: Hostel Room Booking System
1. Project Topic & User Flows
The system is designed to make an automatic hostel management, streamlining the interaction between guests and administrators. 
•	Room Search: Users can filter available rooms based on type and specific dates to check real-time availability.
•	Booking Creation: After selecting a room, the system uses a ReservationBuilder to collect guest’s information and generate a booking entry.
•	Payment & Confirmation: The system calculates the total cost via PricingPolicy and processes the transaction to finalize the reservation status.
2. Database Schema
The database utilizes a relational model to ensure data integrity and complex relationship management.
•	guests: Stores personal identification, like Guest ID, name, and contact information.
•	rooms: Contains data about rooms, such as Room Number, Category, Price, and current Availability Status.
•	reservations: Acts as the central link, connecting Guests to Rooms with specific Check-in/Check-out dates and Reservation Status.
•	payments: Tracks financial history, linking Payment ID to Reservation ID, including the amount, payment method, and timestamp.
3. Architecture & Design Patterns
The project follows a layered architecture to ensure maintainability and scalability. 
Implemented Patterns:
•	Singleton: Ensures a single point of truth for pricing logic, preventing inconsistent rate calculations across the system.
Location: Likely within a dedicated Pricing or Finance service class.
Implementation: It acts as a global point of access to ensure that whether a guest is booking a "Single" or "Deluxe" room, the multipliers (e.g., seasonal rates or discounts) are applied consistently across the entire application.
Benefit: Prevents different parts of the system from using outdated or conflicting price rates.
•	Builder: Handles the step-by-step construction of a Reservation object, making the code readable and managing optional parameters efficiently.
Location: Within the ReservationComponent or a specific Services folder.
Implementation: Instead of a single, massive constructor with ten different parameters (Guest Name, Room ID, Check-in, Check-out, Breakfast option, etc.), the code uses ReservationBuilder to set these values one by one.
Usage: You can see this in the flow where a user selects a room and the system begins "building" the reservation object before saving it to the database.
•	Factory: Encapsulates the instantiation logic for different room types, allowing the system to create new room objects without coupled dependencies.
Location: Within the RoomManagementComponent.
Implementation: The RoomFactory is responsible for creating instances of SingleRoom, DoubleRoom, or DeluxeRoom classes.
Usage: When the system loads room data from the database or when an administrator adds a new room, the RoomFactory determines which object type to instantiate based on the "Type" field, keeping the rest of the code decoupled from specific room class names.
4. Component Principles
The system is designed according to the REP, CCP, and CRP principles to ensure modularity. 
REP - Release/Reuse Equivalency Principle
CCP - Common Closure Principle
CRP - Common Reuse Principle
Reservation Component
•	REP: The booking logic is decoupled from the UI, making it reusable for other booking domains like hotel or car rentals.
•	CCP: Classes related to booking states and validation are grouped, so changes in business rules only affect this component.
•	CRP: The component depends on payment interfaces rather than concrete implementations, avoiding unnecessary re-deployments.
Room Management Component
•	REP: This module manages inventory and can be integrated into any property management system regardless of the property type.
•	CCP: Logic for room availability and room-type definitions are encapsulated here to localize changes.
•	CRP: It remains independent of Guest and Payment data, focusing strictly on room inventory.
AccountingComponent
•	REP: The payment processing module is a generic financial unit that can be extracted for use in other company projects.
•	CCP: All financial calculations, tax logic, and transaction logging are kept together for consistency.
•	CRP: It relies only on the IReservation interface to pull necessary totals, remaining unaware of specific room or guest details.
5. Summary of Recent Changes
•	Optimization: Replaced standard arithmetic for price multipliers with bitwise shift operations where applicable to improve performance in high-load scenarios. 
•	String Processing: Refactored search queries using x86-64 string primitives (SCAS, CMPS) for faster memory-to-memory data handling. 
•	Refactoring: Migrated manual object instantiation to the RoomFactory and ReservationBuilder patterns to reduce code complexity. 



Implemented using Java, JDBC (PostgreSQL/Supabase) and SOLID principles.

